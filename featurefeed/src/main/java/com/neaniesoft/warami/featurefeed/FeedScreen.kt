package com.neaniesoft.warami.featurefeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun FeedScreen(viewModel: FeedViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.onRefresh()
    }
}
