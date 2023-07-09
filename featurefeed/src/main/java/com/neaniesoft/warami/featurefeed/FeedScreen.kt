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
import com.neaniesoft.warami.common.models.Resource
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

    val postsResource by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onRefresh()
    }

    val refreshIndicatorState = rememberPullRefreshState(
        refreshing = postsResource is Resource.Loading,
        onRefresh = { viewModel.onRefresh() }
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshIndicatorState)
        ) {
            when (val postsResource = postsResource) {
                is Resource.Loading -> item { }
                is Resource.Error -> item {
                    Text(
                        text = postsResource.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is Resource.Success -> items(items = postsResource.data) { post ->
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
        PullRefreshIndicator(
            refreshing = postsResource is Resource.Loading,
            state = refreshIndicatorState
        )
    }
}
