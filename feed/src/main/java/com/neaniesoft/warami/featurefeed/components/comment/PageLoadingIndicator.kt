package com.neaniesoft.warami.featurefeed.components.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.featurefeed.PageLoadingContent
import com.neaniesoft.warami.featurefeed.R

@Composable
fun PageLoadingIndicator(
    isRefreshing: Boolean,
    pageLoadingContent: PageLoadingContent,
    onNextPageClicked: (PostId, CommentId?) -> Unit,
    postId: PostId,
    parentCommentId: CommentId?,
) {
    if (!isRefreshing) {
        when (pageLoadingContent) {
            is PageLoadingContent.MaybeMoreResults -> {
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
            is PageLoadingContent.LoadingNextPage -> {
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
            is PageLoadingContent.NoResults -> {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}
