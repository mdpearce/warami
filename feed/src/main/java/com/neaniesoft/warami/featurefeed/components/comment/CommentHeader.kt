package com.neaniesoft.warami.featurefeed.components.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.neaniesoft.warami.common.models.Score
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.CommentIcons

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
            text = creatorName,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
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
        Text(
            text = time,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

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
