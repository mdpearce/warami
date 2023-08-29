package com.neaniesoft.warami.featurefeed.components.comment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.PageLoadingContent
import com.neaniesoft.warami.featurefeed.components.card.PostCardContent
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentsScreenContent(
    postId: PostId,
    parentCommentId: CommentId?,
    pageLoadingContent: PageLoadingContent,
    isRefeshing: Boolean,
    commentsWithDepth: List<Pair<Comment, Int>>,
    post: Post?,
    onCommunityNameClicked: (CommunityId) -> Unit,
    onLinkClicked: (UriString) -> Unit,
    onLoadMoreCommentsClicked: (PostId, CommentId) -> Unit,
    onNextPageClicked: (PostId, CommentId?) -> Unit,
    onRefreshSelected: (PostId, CommentId?) -> Unit,
) {
    val maxDepth =
        remember(commentsWithDepth) { commentsWithDepth.maxByOrNull { it.second }?.second ?: 0 }

    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefeshing, onRefresh = { onRefreshSelected(postId, parentCommentId) })

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(state = listState) {
            item {
                if (post != null) {
                    with(post) {
                        PostCardContent(
                            communityId = community.id,
                            communityName = community.title,
                            creatorName = creator.displayName ?: creator.name,
                            creatorAvatar = creator.avatarUrl,
                            postedTime = publishedAt.formatPeriod(
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
                            onCommunityNameClicked = onCommunityNameClicked,
                            onLinkClicked = onLinkClicked,
                        )
                    }
                }
            }

            items(commentsWithDepth.size) { index ->
                val (comment, depth) = commentsWithDepth[index] // account for post header item
                CommentRow(
                    postId = comment.postId,
                    commentId = comment.commentId,
                    creatorName = comment.creator.displayName ?: comment.creator.name,
                    creatorAvatarUri = comment.creator.avatarUrl,
                    score = comment.counts.score,
                    time = comment.publishedAt.formatPeriod(
                        LocalContext.current.resources,
                        Instant.now().atZone(ZoneId.of("UTC")),
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

            item {
                PageLoadingIndicator(isRefeshing, pageLoadingContent, onNextPageClicked, postId, parentCommentId)
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PullRefreshIndicator(refreshing = isRefeshing, state = pullRefreshState)
    }
}
