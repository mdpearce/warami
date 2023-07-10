package com.neaniesoft.warami.data.repositories.post

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.DomainCommunity
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.Resource
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.di.DatabaseScope
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.adapters.toApi
import com.neaniesoft.warami.data.repositories.adapters.toDb
import com.neaniesoft.warami.data.repositories.adapters.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject
import retrofit2.HttpException
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    private val localDateTimeFormatter: DateTimeFormatter,
    private val coroutineDispatcher: IODispatcher = Dispatchers.IO,
) {
    companion object {
        private const val POSTS_PER_PAGE = 20
    }

    private fun SelectBySearchParams.toDomain(searchParameters: PostSearchParameters): Post =
        this.toDomain(localDateTimeFormatter, searchParameters)

    private fun DomainCommunity.toDb(): DbCommunity = this.toDb(localDateTimeFormatter)

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
                                    SortIndex(initialSortIndex + index + 1),
                                    PageNumber(page),
                                    ZonedDateTime.now() // TODO: Use a clock
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
                postQueries.upsert(
                    id = id.value.toLong(),
                    sortIndex = sortIndex.value.toLong(),
                    pageNum = pageNum.value.toLong(),
                    insertedAt = insertedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                    name = name,
                    creatorId = creator.id.value.toLong(),
                    communityId = community.id.value.toLong(),
                    isRemoved = isRemoved.toLong(),
                    isLocked = isLocked.toLong(),
                    publishedAt = publishedAt.format(localDateTimeFormatter),
                    isDeleted = isDeleted.toLong(),
                    isNsfw = isNsfw.toLong(),
                    apId = apId,
                    isLocal = isLocal.toLong(),
                    languageId = languageId.toLong(),
                    isFeaturedCommunity = isFeaturedCommunity.toLong(),
                    url = url?.value,
                    body = body,
                    updatedAt = updatedAt?.format(localDateTimeFormatter),
                    embedTitle = embedTitle,
                    embedDescription = embedDescription,
                    thumbnailUrl = thumbnail?.value,
                    embedVideoUrl = embedVideo?.value,
                    isCreatorBannedFromCommunity = isCreatorBannedFromCommunity.toLong(),
                    aggregates = aggregates.id.toLong(),
                    subscribedType = subscribedTyped.value,
                    isSaved = isSaved.toLong(),
                    isRead = isRead.toLong(),
                    isCreatorBlocked = isCreatorBlocked.toLong(),
                    myVote = myVote?.toLong(),
                    searchParams = searchParameters.id,
                )
            }
        }
    }
}
