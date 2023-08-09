package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.UriString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    postId: PostId,
    communityId: CommunityId,
    communityName: String,
    creatorName: String,
    creatorAvatar: UriString?,
    postedTime: String,
    communityThumbnailUri: UriString?,
    postTitle: String,
    postThumbnailUri: UriString?,
    postUri: UriString?,
    commentCount: Int,
    score: Int,
    embeddedText: String?,
    isFeaturedInCommunity: Boolean,
    isFeaturedInLocal: Boolean,
    onCardClicked: (PostId) -> Unit,
    onCommunityNameClicked: (CommunityId) -> Unit,
    onLinkClicked: (UriString) -> Unit,
) {
    Card(
        onClick = { onCardClicked(postId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.padding(bottom = 16.dp),
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                PostHeaderRow(
                    communityId = communityId,
                    communityName = communityName,
                    creatorName = creatorName,
                    creatorAvatarUrl = creatorAvatar,
                    postedTime = postedTime,
                    thumbnailUrl = communityThumbnailUri?.value,
                    onCommunityNameClicked = onCommunityNameClicked,
                )

                PostContentRow(
                    postTitle = postTitle,
                    thumbnailUrl = postThumbnailUri,
                    url = postUri,
                    isFeaturedCommunity = isFeaturedInCommunity,
                    isFeaturedLocal = isFeaturedInLocal,
                    onLinkClicked = onLinkClicked,
                )

                if (!embeddedText.isNullOrEmpty()) {
                    PostExtendedContentTextRow(embeddedText)
                }

                PostSummaryRow(commentCount = commentCount, score = score)
            }
        }
    }
}

@Preview
@Composable
fun PreviewPostCard() {
    Surface(modifier = Modifier.fillMaxSize()) {
        PostCard(
            PostId(1),
            CommunityId(1),
            "communityName",
            "creatorName",
            null,
            "12h",
            null,
            "Title of the post.",
            null,
            UriString("https://google.com"),
            376,
            2436,
            "Lorem ipsum foobar uhwef eakjf kje fksb kjf sieufh kswerjhgfiuwhrg wesn foje foih rdiu",
            true,
            true,
            {},
            {},
            {},
        )
    }
}
