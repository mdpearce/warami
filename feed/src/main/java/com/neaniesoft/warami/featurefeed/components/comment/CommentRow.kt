package com.neaniesoft.warami.featurefeed.components.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.ChildCount
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.Score
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.shapes.SpeechBubbleShape

@Composable
fun CommentRow(
    postId: PostId,
    commentId: CommentId,
    creatorName: String,
    creatorAvatarUri: UriString?,
    score: Score,
    time: String,
    body: String,
    depth: Int,
    maxDepth: Int,
    childCount: ChildCount,
    onLoadMoreCommentsClicked: (PostId, CommentId) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        if (depth >= 1) {
            repeat((1..depth).count()) {
                val width = if (it == 0) {
                    11.dp
                } else {
                    12.dp
                }
                Spacer(modifier = Modifier.width(width))
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                )
            }
        }

        Surface(tonalElevation = 4.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    CommentHeader(
                        creatorName = creatorName,
                        creatorAvatarUri = creatorAvatarUri,
                        score = score,
                        time = time,
                    )
                    Surface(
                        modifier = Modifier.padding(top = 8.dp),
                        tonalElevation = 16.dp,
                        shadowElevation = 2.dp,
                        shape = SpeechBubbleShape(triangleSize = 6.dp),
                    ) {
                        Text(
                            text = body,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 12.dp,
                                bottom = 6.dp,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                if (depth == maxDepth && childCount.value > 0) {
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
                                onLoadMoreCommentsClicked(postId, commentId)
                            },
                        tonalElevation = 16.dp,
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = pluralStringResource(
                                id = R.plurals.load_more_comments,
                                count = childCount.value,
                                childCount.value,
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CommentRowPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            CommentRow(
                postId = PostId(1),
                commentId = CommentId(1),
                creatorName = "Some person",
                creatorAvatarUri = null,
                score = Score(50),
                time = "12 hours",
                body = "Lorem ipsum dolor etc ueafh siueh fiushr giush ifuhage ifygsirgh iksh rfousoijf pdotijgpidjhrgkfhsge fuygsefy gsiurhg osr jglih eroughwiregf iwh grefkiuwh rogh woruhg ikwuehfiwug rigfh",
                depth = 8,
                maxDepth = 8,
                childCount = ChildCount(3),
                onLoadMoreCommentsClicked = { _, _ -> },
            )
        }
    }
}
