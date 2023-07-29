package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.PostIcons

@Composable
fun PostContentRow(postTitle: String, thumbnailUrl: UriString?, url: UriString?, isFeaturedCommunity: Boolean, isFeaturedLocal: Boolean) {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = postTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PostContentRowThumbnail(thumbnailUrl = thumbnailUrl, url = url)
            Row {
                if (isFeaturedCommunity) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(16.dp),
                        imageVector = PostIcons.rememberPushPin(),
                        contentDescription = stringResource(id = R.string.content_description_featured_community),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                if (isFeaturedLocal) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = PostIcons.rememberPushPin(),
                        contentDescription = stringResource(id = R.string.content_description_featured_local),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PostContentRowPreview() {
    MaterialTheme {
        Surface(Modifier.fillMaxWidth()) {
            PostContentRow(
                postTitle = "This is a long post title that will spill over to multiple lines",
                thumbnailUrl = null,
                url = UriString("http://google.com"),
                isFeaturedCommunity = true,
                isFeaturedLocal = true,
            )
        }
    }
}
