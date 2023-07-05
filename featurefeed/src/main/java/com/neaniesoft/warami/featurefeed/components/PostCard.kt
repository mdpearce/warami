package com.neaniesoft.warami.featurefeed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.featurefeed.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    communityName: String,
    creatorName: String,
    postedTime: String,
    thumbnailUrl: String?
) {
    Card(
        onClick = {},
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                PostHeaderRow(
                    communityName = communityName,
                    creatorName = creatorName,
                    postedTime = postedTime,
                    thumbnailUrl = thumbnailUrl
                )
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


@Preview
@Composable
fun PreviewPostCard() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PostCard("communityName", "creatorName", "12h", null)
    }
}
