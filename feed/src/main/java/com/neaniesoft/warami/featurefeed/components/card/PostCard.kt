package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.UriString

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
    embeddedText: String?,
) {
    Card(
        onClick = {},
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.padding(bottom = 16.dp),
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                PostHeaderRow(
                    communityName = communityName,
                    creatorName = creatorName,
                    postedTime = postedTime,
                    thumbnailUrl = communityThumbnailUri?.value,
                )

                PostContentRow(
                    postTitle = postTitle,
                    thumbnailUrl = postThumbnailUri,
                    url = postUri,
                )

                if (!embeddedText.isNullOrEmpty()) {
                    PostExtendedContentTextRow(embeddedText)
                }

                PostSummaryRow(commentCount = commentCount, score = score)
            }
        }
    }
}


@Composable
fun PostExtendedContentTextRow(textContent: String) {
    Surface(modifier = Modifier.padding(16.dp).fillMaxWidth(), shape = MaterialTheme.shapes.medium, tonalElevation = 8.dp) {
        Text(modifier = Modifier.padding(16.dp), text = textContent, style = MaterialTheme.typography.bodyMedium)
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
            "Lorem ipsum foobar uhwef eakjf kje fksb kjf sieufh kswerjhgfiuwhrg wesn foje foih rdiu"
        )
    }
}
