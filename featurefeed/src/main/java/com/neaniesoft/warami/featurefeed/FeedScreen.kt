package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.neaniesoft.warami.common.viewModel
import com.neaniesoft.warami.featurefeed.components.PostCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import me.tatarka.inject.annotations.Inject

typealias FeedScreen = @Composable () -> Unit

@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Inject
@Composable
fun FeedScreen(feedViewModel: () -> FeedViewModel) {
    val viewModel = viewModel {
        feedViewModel()
    }

    val posts = viewModel.posts.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    val refreshIndicatorState = rememberPullRefreshState(
        refreshing = posts.loadState.refresh == LoadState.Loading,
        onRefresh = { posts.refresh() },
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshIndicatorState),
        ) {
            items(count = posts.itemCount) { index ->
                val post = posts[index]
                if (post != null) {
                    PostCard(
                        communityName = post.community.name,
                        creatorName = post.creator.name,
                        postedTime = "12h",
                        communityThumbnailUri = post.thumbnail,
                        postTitle = post.name,
                        postThumbnailUri = post.thumbnail,
                        postUri = post.url,
                        commentCount = post.aggregates.commentCount,
                        score = post.aggregates.score,
                    )
                }
            }

            if (posts.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = posts.loadState.refresh == LoadState.Loading,
            state = refreshIndicatorState,
        )
    }
}
