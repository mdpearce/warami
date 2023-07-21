package com.neaniesoft.warami.featurefeed

import androidx.compose.runtime.Composable
import com.neaniesoft.warami.common.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CommentsScreen(
    commentsViewModel: () -> CommentsViewModel,
    navigator: DestinationsNavigator,
) {
    val viewModel = viewModel {
        commentsViewModel()
    }


}
