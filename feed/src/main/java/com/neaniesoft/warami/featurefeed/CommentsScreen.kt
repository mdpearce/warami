package com.neaniesoft.warami.featurefeed

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.extensions.formatPeriod
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.Score
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.viewModel
import com.neaniesoft.warami.featurefeed.components.icons.CommentIcons
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Destination
@Composable
fun CommentsScreen(
    postId: PostId,
    commentsViewModel: () -> CommentsViewModel,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel {
        commentsViewModel()
    }

    Log.d("CommentsScreen", "PostId: $postId")
    LaunchedEffect(key1 = postId) {
        Log.d("CommentsScreen", "refreshing $postId")
        viewModel.initialFetch(postId)
    }

    val commentsWithDepth by viewModel.comments.collectAsState()

    CommentsScreenContent(commentsWithDepth = commentsWithDepth)
}

@Composable
fun CommentsScreenContent(
    commentsWithDepth: List<Pair<Comment, Int>>,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(commentsWithDepth.size) { index ->
                val (comment, depth) = commentsWithDepth[index]
                CommentRow(
                    creatorName = comment.creator.displayName ?: comment.creator.name,
                    creatorAvatarUri = comment.creator.avatarUrl,
                    score = comment.counts.score,
                    time = comment.publishedAt.formatPeriod(
                        LocalContext.current.resources, Instant.now().atZone(ZoneId.of("UTC")),
                    ),
                    body = comment.content,
                    depth = depth,
                )
                val nextDepth = commentsWithDepth.getOrNull(index + 1)?.second
                if (nextDepth != depth) {
                    Divider()
                }
            }
        }
    }
}

data class CommentForDisplay(
    val creatorName: String,
    val creatorAvatarUri: UriString?,
    val score: Score,
    val time: ZonedDateTime,
    val body: String,
    val depth: Int,
)

@Composable
fun CommentRow(
    creatorName: String,
    creatorAvatarUri: UriString?,
    score: Score,
    time: String,
    body: String,
    depth: Int,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        if (depth >= 1) {
            repeat((1..depth).count()) {
                val width = if (it == 0) {
                    15.dp
                } else {
                    16.dp
                }
                Spacer(modifier = Modifier.width(width))
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                )
            }
        }


        Surface(tonalElevation = (depth + 1).dp) {

            Column(modifier = Modifier.padding(16.dp)) {
                CommentHeader(creatorName = creatorName, creatorAvatarUri = creatorAvatarUri, score = score, time = time)
                Surface(
                    modifier = Modifier.padding(top = 8.dp),
                    tonalElevation = (depth + 1 + 8).dp,
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

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
                creatorName = "Some person",
                creatorAvatarUri = null,
                score = Score(50),
                time = "12 hours",
                body = "Lorem ipsum dolor etc ueafh siueh fiushr giush ifuhage ifygsirgh iksh rfousoijf pdotijgpidjhrgkfhsge fuygsefy gsiurhg osr jglih eroughwiregf iwh grefkiuwh rogh woruhg ikwuehfiwug rigfh",
                depth = 3,
            )
        }
    }
}

@Composable
fun CommentHeader(
    creatorName: String,
    creatorAvatarUri: UriString?,
    score: Score,
    time: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (creatorAvatarUri == null) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = CommentIcons.rememberPerson_2(),
                contentDescription = stringResource(id = R.string.content_description_avatar_icon),
            )
        } else {
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(creatorAvatarUri.value)
                    .build(),
                contentDescription = stringResource(id = R.string.content_description_avatar_icon),
                contentScale = ContentScale.Crop,
                placeholder = rememberVectorPainter(image = CommentIcons.rememberPerson_2()),
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = creatorName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = score.value.toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = "•",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(text = time, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)

    }
}

@Preview
@Composable
fun CommentHeaderPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {

            CommentHeader(creatorName = "Some person", creatorAvatarUri = null, score = Score(50), time = "12 hours")

        }
    }
}
