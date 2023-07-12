package com.neaniesoft.warami.data.repositories.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.cash.sqldelight.Transacter
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.db.SelectPostsOffset
import java.time.Instant
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val searchParameters: PostSearchParameters,
    private val postRepository: PostRepository,
    private val transactor: Transacter,
) : RemoteMediator<Int, SelectPostsOffset>() {

    companion object {
        const val POSTS_PER_PAGE = 20
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, SelectPostsOffset>): MediatorResult {
        return try {
            val loadKey: Long = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = postRepository.getRemoteKey(searchParameters)
                    remoteKey.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val posts = postRepository.getFreshPosts(searchParameters, PageNumber(loadKey.toInt()), POSTS_PER_PAGE)

            transactor.transaction {
                if (loadType == LoadType.REFRESH) {
                    postRepository.clearRemoteKey(searchParameters)
                    postRepository.emptyCache(searchParameters)
                }

                postRepository.updateRemoteKey(searchParameters, PageNumber((loadKey + 1).toInt()))

                posts.forEach { post ->
                    postRepository.updatePostInCache(post)
                }
            }

            MediatorResult.Success(endOfPaginationReached = posts.isEmpty())
        } catch (e: PostRepositoryException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val lastUpdated = postRepository.getLatestInsertTime(searchParameters)
        val cacheTimeout = 1.seconds.inWholeMilliseconds
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
