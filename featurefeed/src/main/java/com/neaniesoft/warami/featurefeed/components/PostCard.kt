package com.neaniesoft.warami.featurefeed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.rememberArrowDownward
import com.neaniesoft.warami.featurefeed.components.icons.rememberArrowUpward
import com.neaniesoft.warami.featurefeed.components.icons.rememberBookmark
import com.neaniesoft.warami.featurefeed.components.icons.rememberModeComment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    communityName: String,
    creatorName: String,
    postedTime: String,
    communityThumbnailUri: UriString?,
    postTitle: String,
    postThumbnailUri: UriString?,
    postUri: UriString?,
    commentCount: Int,
    score: Int,
) {
    Card(
        onClick = {},
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                PostHeaderRow(
                    communityName = communityName,
                    creatorName = creatorName,
                    postedTime = postedTime,
                    thumbnailUrl = communityThumbnailUri?.value
                )

                PostContentRow(
                    postTitle = postTitle,
                    thumbnailUrl = postThumbnailUri,
                    url = postUri
                )

                PostSummaryRow(commentCount = commentCount, score = score)
            }
        }
    }
}

@Composable
fun PostHeaderRow(
    communityName: String,
    creatorName: String,
    postedTime: String,
    thumbnailUrl: String?,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (thumbnailUrl == null) {
            Image(
                painter = painterResource(id = R.drawable.baseline_circle_24),
                contentDescription = stringResource(
                    R.string.content_description_community_icon
                ),
                modifier = Modifier.size(24.dp)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.content_description_community_icon),
                placeholder = painterResource(id = R.drawable.baseline_circle_24),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = communityName,
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = creatorName,
            modifier = Modifier.padding(start = 24.dp),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = postedTime,
            modifier = Modifier.padding(start = 24.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun PostContentRow(postTitle: String, thumbnailUrl: UriString?, url: UriString?) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = postTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        PostContentRowThumbnail(thumbnailUrl = thumbnailUrl, url = url)
    }
}

@Composable
fun PostContentRowThumbnail(thumbnailUrl: UriString?, url: UriString?) {
    if (thumbnailUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(true).build(),
            contentDescription = stringResource(
                id = R.string.content_description_post_thumbnail
            ),
            placeholder = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(72.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    } else if (url != null) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.content_description_post_icon),
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.medium), contentScale = ContentScale.FillBounds
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_link_24),
                contentDescription = stringResource(id = R.string.content_description_post_link),
                modifier = Modifier
                    .size(48.dp)
                    .rotate(-45f)
            )
        }
    }
}

@Composable
fun PostSummaryRow(commentCount: Int, score: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            rememberModeComment(),
            contentDescription = stringResource(id = R.string.content_description_comments_icon),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = commentCount.toString(), style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        Icon(
            rememberArrowUpward(),
            contentDescription = stringResource(id = R.string.content_description_upvote_icon),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Icon(
            rememberArrowDownward(),
            contentDescription = stringResource(R.string.content_description_down_vote_icon),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            rememberBookmark(),
            contentDescription = stringResource(R.string.content_description_save_icon),
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Preview
@Composable
fun PreviewPostCard() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PostCard(
            "communityName",
            "creatorName",
            "12h",
            null,
            "Title of the post.",
            null,
            UriString("https://google.com"),
            376,
            2436,
        )
    }
}
