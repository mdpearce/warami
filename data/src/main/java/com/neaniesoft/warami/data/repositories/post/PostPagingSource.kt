package com.neaniesoft.warami.data.repositories.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.compareTo
import com.neaniesoft.warami.common.models.minus
import com.neaniesoft.warami.common.models.plus
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.repositories.adapters.toDomain
import java.io.IOException
import java.time.format.DateTimeFormatter

class PostPagingSource(
    private val postQueries: PostQueries,
    private val searchParameters: PostSearchParameters,
    private val dateTimeFormatter: DateTimeFormatter,
) : PagingSource<PageNumber, Post>() {
    override fun getRefreshKey(state: PagingState<PageNumber, Post>): PageNumber? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<PageNumber>): LoadResult<PageNumber, Post> {
        return try {
            val pageNumber = params.key ?: PageNumber(1)
            val posts = postQueries.transactionWithResult {
                postQueries.selectBySearchParamsForPage(searchParamsId = searchParameters.id, pageNum = pageNumber.value.toLong())
                    .executeAsList().map {
                        it.toDomain(dateTimeFormatter, searchParameters)
                    }
            }
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
    postQueries: PostQueries,
    api: DefaultApi,
    dateTimeFormatter: DateTimeFormatter,
): Pager<PageNumber, Post> {
    return Pager(
        config = PagingConfig(pageSize = PostRemoteMediator.POSTS_PER_PAGE, prefetchDistance = 20),
        remoteMediator = PostRemoteMediator(searchParameters, postQueries, api, dateTimeFormatter),
    ) {
        PostPagingSource(postQueries, searchParameters, dateTimeFormatter)
    }
}
