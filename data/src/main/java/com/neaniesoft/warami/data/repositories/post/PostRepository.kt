package com.neaniesoft.warami.data.repositories.post

import androidx.paging.PagingSource
import app.cash.sqldelight.paging3.QueryPagingSource
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostRemoteKey
import com.neaniesoft.warami.data.db.PostRemoteKeyQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SelectPostsOffset
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.AccountRepository
import com.neaniesoft.warami.data.repositories.ApiRepository
import com.neaniesoft.warami.data.repositories.adapters.toApi
import com.neaniesoft.warami.data.repositories.adapters.toDb
import retrofit2.HttpException
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository
@Inject
constructor(
    private val apiRepository: ApiRepository,
    private val postQueries: PostQueries,
    private val communityQueries: CommunityQueries,
    private val personQueries: PersonQueries,
    private val postAggregateQueries: PostAggregateQueries,
    private val postSearchParamsQueries: PostSearchParamsQueries,
    private val postRemoteKeyQueries: PostRemoteKeyQueries,
    private val coroutineDispatcher: IODispatcher,
    private val clock: Clock,
    private val accountRepository: AccountRepository,
) {

    private val api: DefaultApi
        get() = apiRepository.api.value

    suspend fun getFreshPosts(
        searchParameters: PostSearchParameters,
        page: PageNumber,
        limit: Int,
    ): List<Post> {
        return try {
            val response = api.getPosts(
                type = searchParameters.listingType?.toApi(),
                sort = searchParameters.sortType?.toApi(),
                page = page.value.toBigDecimal(),
                limit = limit.toBigDecimal(),
                communityId = searchParameters.communityId?.value?.toBigDecimal(),
                communityName = searchParameters.communityName,
                savedOnly = searchParameters.isSavedOnly,
                auth = accountRepository.authToken(),
            )
            if (!response.isSuccessful) {
                throw PostRepositoryException("Unsuccessful response from API when fetching fresh posts: ${response.code()}")
            }
            val body = response.body() ?: throw PostRepositoryException("API response was empty while fetching fresh posts")
            body.posts.map { postView ->
                postView.toDomain(searchParameters, clock.instant().atZone(ZoneId.of("UTC")))
            }
        } catch (e: IOException) {
            throw PostRepositoryException("IO error communicating with API", e)
        } catch (e: HttpException) {
            throw PostRepositoryException("HTTP error communicating with API", e)
        }
    }

    fun emptyCache(searchParameters: PostSearchParameters) {
        postQueries.transaction {
            postQueries.deleteBySearchParams(searchParameters.id)
        }
    }

    fun updatePostInCache(post: Post) {
        postQueries.transaction {
            upsertPost(post)
        }
    }

    fun getLatestInsertTime(searchParameters: PostSearchParameters): ZonedDateTime {
        return postQueries.transactionWithResult {
            postQueries.selectLatestInsertTimeForSearchParams(searchParameters.id).executeAsOneOrNull()?.parseZonedDateTime()
                ?: ZonedDateTime.ofInstant(
                    Instant.EPOCH,
                    ZoneId.of("UTC"),
                )
        }
    }

    fun getRemoteKey(searchParameters: PostSearchParameters): PostRemoteKey {
        return postRemoteKeyQueries.transactionWithResult {
            postRemoteKeyQueries.remoteKeyBySearchParams(searchParameters.id).executeAsOne()
        }
    }

    fun clearRemoteKey(searchParameters: PostSearchParameters) {
        postRemoteKeyQueries.transaction {
            postRemoteKeyQueries.deleteBySearchParams(searchParameters.id)
        }
    }

    fun updateRemoteKey(searchParameters: PostSearchParameters, page: PageNumber) {
        postRemoteKeyQueries.transaction {
            postRemoteKeyQueries.insert(searchParameters.id, page.value.toLong())
        }
    }

    fun pagingSource(searchParameters: PostSearchParameters): PagingSource<Int, SelectPostsOffset> {
        return QueryPagingSource(
            countQuery = postQueries.count(searchParameters.id),
            transacter = postQueries,
            context = coroutineDispatcher,
            queryProvider = { limit, offset ->
                postQueries.selectPostsOffset(searchParameters.id, limit, offset)
            },
        )
    }

    private fun upsertPost(post: Post) {
        postQueries.transaction {
            with(post.community) {
                communityQueries.upsert(
                    name = name,
                    title = title,
                    isRemoved = isRemoved.toLong(),
                    published = publishedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    isDeleted = isDeleted.toLong(),
                    isNsfw = isNsfw.toLong(),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isHidden = isHidden.toLong(),
                    isPostingRestrictedToMods = isPostingRestrictedToMods.toLong(),
                    instanceId = instanceId.value.toLong(),
                    description = description,
                    updatedAt = updatedAt?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    iconUrl = icon?.value,
                    bannerUrl = banner?.value,
                    id = id.value.toLong(),
                )
            }

            with(post.creator) {
                personQueries.upsert(
                    name = name,
                    isBanned = isBanned.toLong(),
                    publishedAt = publishedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isDeleted = isDeleted.toLong(),
                    isAdmin = isAdmin.toLong(),
                    instanceId = instanceId.value.toLong(),
                    displayName = displayName,
                    avatarUrl = avatarUrl?.value,
                    updatedAt = updatedAt?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    bio = bio,
                    bannerUrl = bannerUrl?.value,
                    matrixUserId = matrixUserId,
                    banExpires = banExpiresAt?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    id = id.value.toLong(),
                    isBotAccount = isBotAccount.toLong(),
                )
            }

            with(post.aggregates) {
                postAggregateQueries.upsert(
                    postId = postId.value.toLong(),
                    comments = commentCount.toLong(),
                    score = score.toLong(),
                    upVotes = votes.up.toLong(),
                    downVotes = votes.down.toLong(),
                    publishedAt = publishedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    newestCommentTimeNecro = newestCommentTimeNecro.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    newestCommentTime = newestComment.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    isFeaturedCommunity = isFeaturedCommunity.toLong(),
                    isFeaturedLocal = isFeaturedLocal.toLong(),
                    hotRank = hotRank.toLong(),
                    hotRankActive = hotRankActive.toLong(),
                    id = id.toLong(),
                )
            }

            with(post.searchParameters) {
                postSearchParamsQueries.upsert(
                    listingType = listingType?.value,
                    sortType = sortType?.value,
                    communityId = communityId?.value?.toLong(),
                    communityName = communityName,
                    isSavedOnly = isSavedOnly?.toLong(),
                    id = id,
                )
            }

            with(post) {
                postQueries.insert(this.toDb())
            }
        }
    }
}

data class PostRepositoryException(override val message: String, override val cause: Throwable? = null) : Exception(message, cause)
