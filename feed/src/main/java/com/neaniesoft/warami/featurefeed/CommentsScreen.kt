package com.neaniesoft.warami.featurefeed

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.featurefeed.components.comment.CommentsScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination
@Composable
fun CommentsScreen(
    postId: PostId,
    parentCommentId: CommentId?,
    navigator: DestinationsNavigator,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
    Timber.d("PostId: $postId")
    val commentsWithDepth by viewModel.comments.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val post by viewModel.post.collectAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        Timber.d("refreshing $postId, parent: $parentCommentId")
        viewModel.initialFetch(postId, parentCommentId)
    }
    val navigation by viewModel.navigation.collectAsState(initial = null)
    val uriNavigation by viewModel.uriNavigation.collectAsState(initial = null)

    LaunchedEffect(key1 = navigation) {
        val direction = navigation
        if (direction != null) {
            navigator.navigate(direction)
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = uriNavigation) {
        val uri = uriNavigation?.value?.toUri()
        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    val pageLoadingContent by viewModel.pageLoadingContent.collectAsState()

    CommentsScreenContent(
        postId = postId,
        post = post,
        parentCommentId = parentCommentId,
        commentsWithDepth = commentsWithDepth,
        pageLoadingContent = pageLoadingContent,
        isRefeshing = isRefreshing,
        onLoadMoreCommentsClicked = viewModel::onLoadMoreCommentsClicked,
        onNextPageClicked = { pagePostId, comment -> viewModel.onLoadNextPage(pagePostId, comment) },
        onRefreshSelected = { pagePostId, pageParentCommentId -> viewModel.refresh(pagePostId, pageParentCommentId) },
        onCommunityNameClicked = { communityId -> viewModel.onCommunityClicked(communityId) },
        onLinkClicked = { uriString -> viewModel.onLinkClicked(uriString) },
    )
}
