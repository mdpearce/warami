package com.neaniesoft.warami.data.repositories.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.toDomain
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.repositories.adapters.toApi
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.hours

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
            val latestPageNum = postQueries.transactionWithResult {
                postQueries.selectLatestPageNumForSearchParams(searchParameters.id).executeAsOneOrNull() ?: 1
            }

            val loadKey: Pair<PageNumber, SortIndex> = when (loadType) {
                LoadType.REFRESH -> Pair(PageNumber(1), SortIndex(0))
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    Pair(PageNumber(latestPageNum.toInt()), lastItem.sortIndex)
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
                    postView.toDomain(
                        searchParameters,
                        SortIndex(loadKey.second.value + 1 + index),
                        loadKey.first,
                        ZonedDateTime.now(),
                    ) // Use a clock for getting now()
                }.forEach { post ->
                    with(post) {
                        postQueries.upsert(
                            id = id.value.toLong(),
                            sortIndex = sortIndex.value.toLong(),
                            pageNum = pageNum.value.toLong(),
                            insertedAt = insertedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME), // TODO: inject this
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

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = postQueries.selectLatestInsertTimeForSearchParams(searchParameters.id).executeAsOneOrNull()?.parseZonedDateTime()
            ?: ZonedDateTime.ofInstant(
                Instant.EPOCH, ZoneId.systemDefault(),
            )
        val cacheTimeout = 1.hours.inWholeMilliseconds
        return if (Instant.now().toEpochMilli() - lastUpdated.toInstant().toEpochMilli() <= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}
