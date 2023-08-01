package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.neaniesoft.warami.featurefeed.components.feed.FeedScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    navigator: DestinationsNavigator,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val listingType by viewModel.listingType.collectAsState()

    val listState = rememberLazyListState()

    val currentTime by viewModel.currentTime.collectAsState()

    val navigation by viewModel.navigation.collectAsState(initial = null)

    LaunchedEffect(key1 = navigation) {
        val destination = navigation
        if (destination != null) {
            Timber.d("Navigating to " + destination.route)
            navigator.navigate(destination)
        }
    }

    Timber.d("About to render screen content: $listState, ${viewModel.posts}, $currentTime, $listingType")

    FeedScreenContent(listState, viewModel.posts, currentTime, { viewModel.onPostClicked(it) }, listingType = listingType)
}
