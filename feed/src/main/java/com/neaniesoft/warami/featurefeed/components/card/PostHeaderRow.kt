package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R

@Composable
fun PostHeaderRow(
    communityName: String,
    creatorName: String,
    creatorAvatarUrl: UriString?,
    postedTime: String,
    thumbnailUrl: String?,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (thumbnailUrl == null) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(
                    R.string.content_description_community_icon,
                ),
                modifier = Modifier.size(24.dp).clip(CircleShape),
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.content_description_community_icon),
                placeholder = painterResource(id = R.drawable.baseline_circle_24),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = communityName,
            modifier = Modifier.padding(start = 8.dp).weight(1.0f),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(0.1f))
        if (creatorAvatarUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(creatorAvatarUrl.value)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.content_description_avatar_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp),
            )
        }
        Text(
            text = creatorName,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = postedTime,
            modifier = Modifier.padding(start = 24.dp),
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview
@Composable
fun PreviewPostHeaderRow() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            PostHeaderRow(
                communityName = "Sample community with an extremely long name to wrap on 2 lines",
                creatorName = "John Doe",
                creatorAvatarUrl = null,
                postedTime = "12 hours",
                thumbnailUrl = null,
            )
        }
    }
}
