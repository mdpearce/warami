package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neaniesoft.warami.featurefeed.components.PostCard
import com.neaniesoft.warami.featurefeed.models.EmptyFeed
import com.neaniesoft.warami.featurefeed.models.ErrorFeed
import com.neaniesoft.warami.featurefeed.models.PostFeed
import com.neaniesoft.warami.featurefeed.models.Refreshing


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.onRefresh()
    }

    val feedList by viewModel.feedList.collectAsState()
    val isRefreshing by viewModel.refreshing.collectAsState()

    val refreshIndicatorState = rememberPullRefreshState(
        refreshing = isRefreshing is Refreshing,
        onRefresh = { viewModel.onRefresh() }
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshIndicatorState)
        ) {
            when (val feedListValue = feedList) {
                is EmptyFeed -> item { }
                is ErrorFeed -> item {
                    Text(
                        text = feedListValue.e.localizedMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is PostFeed -> items(items = feedListValue.posts) { post ->
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
        }
        PullRefreshIndicator(refreshing = isRefreshing is Refreshing, state = refreshIndicatorState)
    }
}
