package com.neaniesoft.warami.data.repositories.post

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.DomainCommunity
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.di.DatabaseModule
import com.neaniesoft.warami.data.repositories.RemoteApiError
import com.neaniesoft.warami.data.repositories.adapters.toApi
import com.neaniesoft.warami.data.repositories.adapters.toDb
import com.neaniesoft.warami.data.repositories.adapters.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

typealias DbCommunity = com.neaniesoft.warami.data.db.Community

@Singleton
class PostRepository @Inject constructor(
    private val api: DefaultApi,
    private val postQueries: PostQueries,
    private val communityQueries: CommunityQueries,
    private val personQueries: PersonQueries,
    private val postAggregateQueries: PostAggregateQueries,
    private val postSearchParamsQueries: PostSearchParamsQueries,
    private val localDateTimeFormatter: DateTimeFormatter,
    @Named(DatabaseModule.DISPATCHER_IO) private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private fun SelectBySearchParams.toDomain(searchParameters: PostSearchParameters): Post =
        this.toDomain(localDateTimeFormatter, searchParameters)

    private fun DomainCommunity.toDb(): DbCommunity = this.toDb(localDateTimeFormatter)

    fun listPostsFlow(
        searchParameters: PostSearchParameters,
        updateFromApi: Boolean
    ): Flow<PostListResult> {

        return flow {
            emit(
                PostList(
                    postQueries.selectBySearchParams(searchParameters.id).executeAsList()
                        .map { it.toDomain(searchParameters) })
            )
            if (updateFromApi) {
                emit(Fetching)
                try {
                    val response = api.getPosts(
                        type = searchParameters.listingType?.toApi(),
                        sort = searchParameters.sortType?.toApi(),
                        page = searchParameters.pageNumber?.toBigDecimal(),
                        limit = searchParameters.postLimit?.toBigDecimal(),
                        communityId = searchParameters.communityId?.value?.toBigDecimal(),
                        communityName = searchParameters.communityName,
                        savedOnly = searchParameters.isSavedOnly,
                        auth = null // TODO add this...
                    )
                    val body = response.body()
                    if (!response.isSuccessful) {
                        emit(
                            ErrorFetching(
                                RemoteApiError(
                                    response.code(),
                                    response.errorBody()?.string() ?: ""
                                )
                            )
                        )
                    } else if (body == null) {
                        emit(ErrorFetching(RemoteApiError(response.code(), "Empty response body")))
                    } else {
                        postQueries.transaction {
                            postQueries.deleteBySearchParams(searchParameters.id)
                            body.posts.forEach { postView ->
                                upsertPost(postView.toDomain(searchParameters))
                            }
                        }
                        emit(PostList(
                            body.posts.map { postView -> postView.toDomain(searchParameters) }
                        ))
                        emit(Finished)
                    }
                } catch (e: IOException) {
                    emit(ErrorFetching(e))
                }
            }
        }.flowOn(coroutineDispatcher)
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
                    id = id.value.toLong()
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
                    isBotAccount = isBotAccount.toLong()
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
                    id = id.toLong()
                )
            }

            with(post.searchParameters) {
                postSearchParamsQueries.upsert(
                    listingType = listingType?.value,
                    sortType = sortType?.value,
                    page = pageNumber?.toLong(),
                    postLimit = postLimit?.toLong(),
                    communityId = communityId?.value?.toLong(),
                    communityName = communityName,
                    isSavedOnly = isSavedOnly?.toLong(),
                    id = id
                )
            }

            with(post) {
                postQueries.upsert(
                    id = id.value.toLong(),
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
                    searchParams = searchParameters.id
                )
            }
        }
    }
}
