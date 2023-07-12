package com.neaniesoft.warami.data.repositories.post

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.cash.sqldelight.Transacter
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.compareTo
import com.neaniesoft.warami.common.models.minus
import com.neaniesoft.warami.common.models.plus
import com.neaniesoft.warami.data.db.SelectPostsOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class PostPagingSource(
    private val postRepository: PostRepository,
    private val searchParameters: PostSearchParameters,
) : PagingSource<PageNumber, Post>() {

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        scope.launch {
            postRepository.invalidate.collect { shouldInvalidate ->
                if (shouldInvalidate) {
                    invalidate()
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<PageNumber, Post>): PageNumber? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
        return null
    }

    override suspend fun load(params: LoadParams<PageNumber>): LoadResult<PageNumber, Post> {
        Log.d("PostPagingSource", "load: key: ${params.key}, loadsize: ${params.loadSize}")
        return try {
            val pageNumber = params.key ?: PageNumber(1)
            val posts = postRepository.getCachedPosts(searchParameters, pageNumber)
            Log.d("PostPagingSource", "loaded ${posts.size} from cache for page number: $pageNumber")
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (posts.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = posts,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
fun createPager(
    searchParameters: PostSearchParameters,
    postRepository: PostRepository,
    postTransactor: PostTransactor,
): Pager<Int, SelectPostsOffset> {

    return Pager(
        config = PagingConfig(pageSize = PostRemoteMediator.POSTS_PER_PAGE, prefetchDistance = 10),
        remoteMediator = PostRemoteMediator(searchParameters, postRepository, postTransactor),
    ) {
        postRepository.pagingSource(searchParameters)
    }
}

typealias PostTransactor = Transacter
