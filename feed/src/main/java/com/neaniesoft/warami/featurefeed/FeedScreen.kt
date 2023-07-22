package com.neaniesoft.warami.featurefeed

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.viewModel
import com.neaniesoft.warami.featurefeed.components.card.PostCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.ZoneId

@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    feedViewModel: () -> FeedViewModel,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel {
        feedViewModel()
    }

    val posts = viewModel.posts.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    val refreshIndicatorState = rememberPullRefreshState(
        refreshing = posts.loadState.refresh == LoadState.Loading,
        onRefresh = { posts.refresh() },
    )

    val currentTime by viewModel.currentTime.collectAsState()

    val navigation by viewModel.navigation.collectAsState(initial = null)

    LaunchedEffect(key1 = navigation) {
        val destination = navigation
        if (destination != null) {
            Log.d("FeedScreen", "Navigating to ${destination.route}")
            navigator.navigate(destination)
        }
    }

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
                        postId = post.postId,
                        communityName = post.community.title,
                        creatorName = post.creator.displayName ?: post.creator.name,
                        creatorAvatar = post.creator.avatarUrl,
                        postedTime = post.publishedAt.formatPeriod(
                            resources = LocalContext.current.resources,
                            comparison = currentTime.atZone(
                                ZoneId.systemDefault(),
                            ),
                        ),
                        communityThumbnailUri = post.community.icon,
                        postTitle = post.name,
                        postThumbnailUri = post.thumbnail,
                        postUri = post.url,
                        commentCount = post.aggregates.commentCount,
                        score = post.aggregates.score,
                        embeddedText = post.body,
                        isFeaturedInCommunity = post.aggregates.isFeaturedCommunity,
                        isFeaturedInLocal = post.aggregates.isFeaturedLocal,
                        onCardClicked = { postId -> viewModel.onPostClicked(postId) },
                    )
                }
            }

            if (posts.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
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
