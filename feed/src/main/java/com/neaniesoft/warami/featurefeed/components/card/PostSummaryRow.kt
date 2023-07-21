package com.neaniesoft.warami.featurefeed.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.PostIcons

@Composable
fun PostSummaryRow(commentCount: Int, score: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp),
    ) {
        Icon(
            PostIcons.rememberModeComment(),
            contentDescription = stringResource(id = R.string.content_description_comments_icon),
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = commentCount.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp),
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
        )
        Icon(
            PostIcons.rememberArrowUpward(),
            contentDescription = stringResource(id = R.string.content_description_upvote_icon),
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        )
        Icon(
            PostIcons.rememberArrowDownward(),
            contentDescription = stringResource(R.string.content_description_down_vote_icon),
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            PostIcons.rememberBookmark(),
            contentDescription = stringResource(R.string.content_description_save_icon),
            modifier = Modifier
                .size(24.dp),
        )
    }
}
