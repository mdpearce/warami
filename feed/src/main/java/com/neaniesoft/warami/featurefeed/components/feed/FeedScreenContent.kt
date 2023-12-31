package com.neaniesoft.warami.featurefeed.components.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.components.card.PostCard
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun FeedScreenContent(
    listState: LazyListState,
    posts: LazyPagingItems<Post>,
    onPostClicked: (PostId) -> Unit,
    onCommunityNameClicked: (CommunityId) -> Unit,
    onLinkClicked: (UriString) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    Timber.d(
        "Recomposing FeedScreenContent: listState: $listState, pagingPosts: $posts, onPostClicked(): $onPostClicked",
    )
    val refreshIndicatorState = rememberPullRefreshState(
        refreshing = posts.loadState.refresh == LoadState.Loading,
        onRefresh = { posts.refresh() },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
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
                            communityId = post.community.id,
                            communityName = post.community.title,
                            creatorName = post.creator.displayName ?: post.creator.name,
                            creatorAvatar = post.creator.avatarUrl,
                            postedTime = post.publishedAt.formatPeriod(
                                resources = LocalContext.current.resources,
                                comparison = ZonedDateTime.now(ZoneId.systemDefault()),
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
                            onCardClicked = { postId -> onPostClicked(postId) },
                            onCommunityNameClicked = onCommunityNameClicked,
                            onLinkClicked = onLinkClicked,
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
}
