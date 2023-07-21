package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R

@Composable
fun PostContentRowThumbnail(thumbnailUrl: UriString?, url: UriString?) {
    if (thumbnailUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).crossfade(true)
                .data(thumbnailUrl.value).build(),
            contentDescription = stringResource(
                id = R.string.content_description_post_thumbnail,
            ),
            placeholder = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(MaterialTheme.shapes.medium),
        )
    } else if (url != null) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(id = R.string.content_description_post_icon),
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.FillBounds,
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_link_24),
                contentDescription = stringResource(id = R.string.content_description_post_link),
                modifier = Modifier
                    .size(48.dp)
                    .rotate(-45f),
            )
        }
    }
}
