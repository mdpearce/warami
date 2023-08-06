package com.neaniesoft.warami.featurefeed.components.feed

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.neaniesoft.warami.featurefeed.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTopBar(communityName: String?) {
    TopAppBar(title = { Text(text = communityName ?: stringResource(id = R.string.screen_title_home)) })
}
