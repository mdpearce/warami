package com.neaniesoft.warami.data.repositories.post

import android.util.Log
import androidx.paging.PagingSource
import app.cash.sqldelight.paging3.QueryPagingSource
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.DomainCommunity
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.Resource
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.common.models.plus
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostRemoteKey
import com.neaniesoft.warami.data.db.PostRemoteKeyQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.db.SelectPostsOffset
import com.neaniesoft.warami.data.di.DatabaseScope
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.adapters.toApi
import com.neaniesoft.warami.data.repositories.adapters.toDb
import com.neaniesoft.warami.data.repositories.adapters.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject
import retrofit2.HttpException
import java.io.IOException
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

typealias DbCommunity = com.neaniesoft.warami.data.db.Community

@Inject
@DatabaseScope
class PostRepository(
    private val api: DefaultApi,
    private val postQueries: PostQueries,
    private val communityQueries: CommunityQueries,
    private val personQueries: PersonQueries,
    private val postAggregateQueries: PostAggregateQueries,
    private val postSearchParamsQueries: PostSearchParamsQueries,
    private val postRemoteKeyQueries: PostRemoteKeyQueries,
    private val localDateTimeFormatter: DateTimeFormatter,
    private val coroutineDispatcher: IODispatcher = Dispatchers.IO,
    private val clock: Clock,
) {
    companion object {
        private const val POSTS_PER_PAGE = 20
    }

    private val _invalidate: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val invalidate = _invalidate.asSharedFlow()

    suspend fun invalidate() {
        _invalidate.emit(true)
    }

    private fun SelectBySearchParams.toDomain(searchParameters: PostSearchParameters): Post =
        this.toDomain(localDateTimeFormatter, searchParameters)

    private fun DomainCommunity.toDb(): DbCommunity = this.toDb(localDateTimeFormatter)

    fun getLatestCachedPageNumber(searchParameters: PostSearchParameters): PageNumber? = postQueries.transactionWithResult {
        postQueries.selectLatestPageNumForSearchParams(searchParameters.id).executeAsOneOrNull()?.let { PageNumber(it.toInt()) }
    }

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
            )
            if (!response.isSuccessful) {
                throw PostRepositoryException("Unsuccessful response from API when fetching fresh posts: ${response.code()}")
            }
            val body = response.body() ?: throw PostRepositoryException("API response was empty while fetching fresh posts")
            return body.posts.map { postView ->
                postView.toDomain(searchParameters, page, clock.instant().atZone(ZoneId.of("UTC")))
            }
        } catch (e: IOException) {
            throw PostRepositoryException("IO error communicating with API", e)
        } catch (e: HttpException) {
            throw PostRepositoryException("HTTP error communicating with API", e)
        }
    }

    fun getCachedPostIds(searchParameters: PostSearchParameters): List<PostId> {
        return postQueries.transactionWithResult {
            postQueries.selectPostIdsForSearchParams(searchParameters.id).executeAsList().map { PostId(it.toInt()) }
        }
    }

    fun emptyCache(searchParameters: PostSearchParameters) {
        postQueries.transaction {
            postQueries.deleteBySearchParams(searchParameters.id)
        }
    }

    fun updatePostInCache(post: Post) {
        Log.d("PostRepository", "Updating post ${post.postId.value} in cache")
        postQueries.transaction {
            upsertPost(post)
        }
    }

    fun getLatestInsertTime(searchParameters: PostSearchParameters): ZonedDateTime {
        return postQueries.transactionWithResult {
            postQueries.selectLatestInsertTimeForSearchParams(searchParameters.id).executeAsOneOrNull()?.parseZonedDateTime()
                ?: ZonedDateTime.ofInstant(
                    Instant.EPOCH, ZoneId.of("UTC"),
                )
        }
    }

    fun getCachedPosts(searchParameters: PostSearchParameters, pageNumber: PageNumber): List<Post> {
        return postQueries.transactionWithResult {
            postQueries.selectBySearchParamsForPage(searchParameters.id, pageNumber.value.toLong()).executeAsList().map {
                it.toDomain(localDateTimeFormatter, searchParameters)
            }
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
            }
        )
    }

    /**
     * Fetch a new page of posts. Posts will be fetched from the API and cached locally before being emitted.
     * If [initialSortIndex] is 0, the fetch will be treated as a refresh and all existing records will be
     * deleted for that search query.
     */
    fun fetchItems(
        page: Int,
        initialSortIndex: Int,
        searchParameters: PostSearchParameters,
    ): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getPosts(
                type = searchParameters.listingType?.toApi(),
                sort = searchParameters.sortType?.toApi(),
                page = page.toBigDecimal(),
                limit = POSTS_PER_PAGE.toBigDecimal(),
                communityId = searchParameters.communityId?.value?.toBigDecimal(),
                communityName = searchParameters.communityName,
                savedOnly = searchParameters.isSavedOnly,
            )
            val body = response.body()
            if (!response.isSuccessful) {
                emit(Resource.Error("Unsuccessful response: ${response.code()}"))
            } else if (body == null) {
                emit(Resource.Error("Empty body"))
            } else {
                val posts = postQueries.transactionWithResult {
                    if (initialSortIndex == 0) {
                        postQueries.deleteBySearchParams(searchParameters.id)
                    }
                    val existingIdsForSearch =
                        postQueries.selectPostIdsForSearchParams(searchParameters.id)
                            .executeAsList()
                    val items =
                        body.posts.filterNot { postView -> existingIdsForSearch.contains(postView.post.id.toLong()) }
                            .mapIndexed { index, postView ->
                                postView.toDomain(
                                    searchParameters,
                                    PageNumber(page),
                                    ZonedDateTime.now(), // TODO: Use a clock
                                )
                            }
                    items.forEach { post ->
                        upsertPost(post)
                    }
                    postQueries.selectBySearchParams(searchParams = searchParameters.id)
                        .executeAsList()
                        .map { it.toDomain(searchParameters) }
                }
                emit(Resource.Success(posts))
            }
        } catch (e: IOException) {
            emit(Resource.Error("IOException: ${e.localizedMessage}"))
        } catch (e: HttpException) {
            emit(Resource.Error("HttpError: ${e.localizedMessage}"))
        }
    }

    private fun upsertPost(post: Post) {
        postQueries.transaction {
            with(post.community) {
                communityQueries.upsert(
                    name = name,
                    title = title,
                    isRemoved = isRemoved.toLong(),
                    published = publishedAt.format(localDateTimeFormatter),
                    isDeleted = isDeleted.toLong(),
                    isNsfw = isNsfw.toLong(),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isHidden = isHidden.toLong(),
                    isPostingRestrictedToMods = isPostingRestrictedToMods.toLong(),
                    instanceId = instanceId.value.toLong(),
                    description = description,
                    updatedAt = updatedAt?.format(localDateTimeFormatter),
                    iconUrl = icon?.value,
                    bannerUrl = banner?.value,
                    id = id.value.toLong(),
                )
            }

            with(post.creator) {
                personQueries.upsert(
                    name = name,
                    isBanned = isBanned.toLong(),
                    publishedAt = publishedAt.format(localDateTimeFormatter),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isDeleted = isDeleted.toLong(),
                    isAdmin = isAdmin.toLong(),
                    instanceId = instanceId.value.toLong(),
                    displayName = displayName,
                    avatarUrl = avatarUrl?.value,
                    updatedAt = updatedAt?.format(localDateTimeFormatter),
                    bio = bio,
                    bannerUrl = bannerUrl?.value,
                    matrixUserId = matrixUserId,
                    banExpires = banExpiresAt?.format(localDateTimeFormatter),
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
                    publishedAt = publishedAt.format(localDateTimeFormatter),
                    newestCommentTimeNecro = newestCommentTimeNecro.format(localDateTimeFormatter),
                    newestCommentTime = newestComment.format(localDateTimeFormatter),
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
                postQueries.insert(this.toDb(localDateTimeFormatter))
            }
        }
    }
}

data class PostRepositoryException(override val message: String, override val cause: Throwable? = null) : Exception(message, cause)
