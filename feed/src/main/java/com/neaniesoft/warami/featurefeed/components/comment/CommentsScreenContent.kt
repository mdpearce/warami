package com.neaniesoft.warami.featurefeed.components.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.featurefeed.PageLoadingContent
import com.neaniesoft.warami.featurefeed.R
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentsScreenContent(
    postId: PostId,
    parentCommentId: CommentId?,
    pageLoadingContent: PageLoadingContent,
    isRefeshing: Boolean,
    commentsWithDepth: List<Pair<Comment, Int>>,
    onLoadMoreCommentsClicked: (PostId, CommentId) -> Unit,
    onNextPageClicked: (PostId, CommentId?) -> Unit,
    onRefreshSelected: (PostId, CommentId?) -> Unit,
) {
    val maxDepth =
        remember(commentsWithDepth) { commentsWithDepth.maxByOrNull { it.second }?.second ?: 0 }

    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefeshing, onRefresh = { onRefreshSelected(postId, parentCommentId) })

    Surface(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)) {

        LazyColumn(state = listState) {
            items(commentsWithDepth.size) { index ->
                val (comment, depth) = commentsWithDepth[index]
                CommentRow(
                    postId = comment.postId,
                    commentId = comment.commentId,
                    creatorName = comment.creator.displayName ?: comment.creator.name,
                    creatorAvatarUri = comment.creator.avatarUrl,
                    score = comment.counts.score,
                    time = comment.publishedAt.formatPeriod(
                        LocalContext.current.resources, Instant.now().atZone(ZoneId.of("UTC")),
                    ),
                    body = comment.content,
                    depth = depth,
                    maxDepth = maxDepth,
                    childCount = comment.counts.childCount,
                    onLoadMoreCommentsClicked = onLoadMoreCommentsClicked,
                )
                val nextDepth = commentsWithDepth.getOrNull(index + 1)?.second
                if (nextDepth != depth) {
                    Divider()
                }
            }


            if (!isRefeshing) {
                when (pageLoadingContent) {
                    is PageLoadingContent.MaybeMoreResults -> {
                        item {
                            val interactionSource = remember {
                                MutableInteractionSource()
                            }
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = rememberRipple(bounded = true),
                                    ) {
                                        onNextPageClicked(postId, parentCommentId)
                                    },
                                tonalElevation = 16.dp,
                            ) {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = stringResource(
                                        id = R.string.load_more_comments_page,
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }

                    is PageLoadingContent.LoadingNextPage -> {
                        item {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                                Spacer(modifier = Modifier.height(72.dp))
                            }
                        }
                    }

                    is PageLoadingContent.NoResults -> {
                        item {
                            Spacer(modifier = Modifier.height(72.dp))
                        }
                    }

                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PullRefreshIndicator(refreshing = isRefeshing, state = pullRefreshState)
    }
}
