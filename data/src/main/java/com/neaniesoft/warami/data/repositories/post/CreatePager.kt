package com.neaniesoft.warami.data.repositories.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.sqldelight.Transacter
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.db.SelectPostsOffset

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
