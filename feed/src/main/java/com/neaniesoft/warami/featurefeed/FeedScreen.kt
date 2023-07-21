package com.neaniesoft.warami.featurefeed

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.viewModel
import com.neaniesoft.warami.featurefeed.components.card.PostCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import me.tatarka.inject.annotations.Inject
import java.time.ZoneId

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

    val currentTime by viewModel.currentTime.collectAsState()

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
