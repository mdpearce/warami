package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.PostIcons

@Composable
fun PostContentLargeImageRow(
    postTitle: String,
    imageUri: UriString,
    isFeaturedInCommunity: Boolean,
    isFeaturedInLocal: Boolean,
    onLinkClicked: (UriString) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Text(
                text = postTitle,
                style = MaterialTheme.typography.titleMedium,
                color = if (isFeaturedInCommunity || isFeaturedInLocal) {
                    MaterialTheme.colorScheme.primary
                } else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            if (isFeaturedInCommunity) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp),
                    imageVector = PostIcons.rememberPushPin(),
                    contentDescription = stringResource(id = R.string.content_description_featured_community),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            if (isFeaturedInLocal) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp),
                    imageVector = PostIcons.rememberPushPin(),
                    contentDescription = stringResource(id = R.string.content_description_featured_local),
                )
            }
        }
        val interactionSource = remember { MutableInteractionSource() }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(true)
                .data(imageUri.value).build(),
            contentDescription = stringResource(
                id = R.string.content_description_post_large_image,
            ),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(interactionSource, indication = LocalIndication.current, onClick = { onLinkClicked(imageUri) }),
        )
    }
}
