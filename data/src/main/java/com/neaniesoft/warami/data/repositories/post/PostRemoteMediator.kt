package com.neaniesoft.warami.data.repositories.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.repositories.adapters.toApi
import retrofit2.HttpException
import java.io.IOException
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val searchParameters: PostSearchParameters,
    private val postQueries: PostQueries,
    private val api: DefaultApi,
    private val dateTimeFormatter: DateTimeFormatter,
) : RemoteMediator<PageNumber, Post>() {

    companion object {
        private const val POSTS_PER_PAGE = 20
    }

    override suspend fun load(loadType: LoadType, state: PagingState<PageNumber, Post>): MediatorResult {
        return try {
            val loadKey: Pair<PageNumber, SortIndex> = when (loadType) {
                LoadType.REFRESH -> Pair(PageNumber(1), SortIndex(0))
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    Pair(lastItem.pageNum, lastItem.sortIndex)
                }
            }

            val response = api.getPosts(
                type = searchParameters.listingType?.toApi(),
                sort = searchParameters.sortType?.toApi(),
                page = loadKey.first.value.toBigDecimal(),
                limit = POSTS_PER_PAGE.toBigDecimal(),
                communityId = searchParameters.communityId?.value?.toBigDecimal(),
                communityName = searchParameters.communityName,
                savedOnly = searchParameters.isSavedOnly,
            )
            if (!response.isSuccessful) {
                return MediatorResult.Error(IllegalStateException("${response.errorBody()?.string()}"))
            }
            val body = response.body() ?: return MediatorResult.Error(IllegalStateException("Response body is null"))
            val apiPosts = body.posts

            postQueries.transaction {
                if (loadType == LoadType.REFRESH) {
                    postQueries.deleteBySearchParams(searchParameters.id)
                }
                apiPosts.mapIndexed { index, postView ->
                    postView.toDomain(searchParameters, SortIndex(loadKey.second.value + 1 + index), loadKey.first)
                }.forEach { post ->
                    with(post) {
                        postQueries.upsert(
                            id = id.value.toLong(),
                            sortIndex = sortIndex.value.toLong(),
                            pageNum = pageNum.value.toLong(),
                            name = name,
                            creatorId = creator.id.value.toLong(),
                            communityId = community.id.value.toLong(),
                            isRemoved = isRemoved.toLong(),
                            isLocked = isLocked.toLong(),
                            publishedAt = publishedAt.format(dateTimeFormatter),
                            isDeleted = isDeleted.toLong(),
                            isNsfw = isNsfw.toLong(),
                            apId = apId,
                            isLocal = isLocal.toLong(),
                            languageId = languageId.toLong(),
                            isFeaturedCommunity = isFeaturedCommunity.toLong(),
                            url = url?.value,
                            body = this.body,
                            updatedAt = updatedAt?.format(dateTimeFormatter),
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

            MediatorResult.Success(endOfPaginationReached = apiPosts.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
