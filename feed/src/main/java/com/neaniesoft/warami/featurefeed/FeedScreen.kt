package com.neaniesoft.warami.featurefeed

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.featurefeed.components.feed.FeedScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@RootNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    navigator: DestinationsNavigator,
    communityId: CommunityId? = null,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val listingType by viewModel.listingType.collectAsState(initial = ListingType.ALL)

    val listState = rememberLazyListState()

    val currentTime by viewModel.currentTime.collectAsState()

    val navigation by viewModel.navigation.collectAsState(initial = null)

    val listingTypeMenuItems by viewModel.listingTypeMenuItems.collectAsState()

    val communityName by viewModel.communityName.collectAsState(initial = null)

    LaunchedEffect(
        key1 = communityId,
        block = {
            viewModel.onCommunityId(communityId)
        },
    )

    val posts = viewModel.postsFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = navigation) {
        val destination = navigation
        if (destination != null) {
            Timber.d("Navigating to " + destination.route)
            navigator.navigate(destination)
        }
    }

    Timber.d("About to render screen content: $listState, $posts, $currentTime, $listingType")

    FeedScreenContent(
        communityName,
        listState,
        posts,
        currentTime,
        { viewModel.onPostClicked(it) },
        listingType = listingType,
        onListingTypeButtonClicked = { viewModel.onListingTypeButtonClicked() },
        listingTypeMenuItems = listingTypeMenuItems,
        onDismissListingTypeMenu = viewModel::onListingTypeMenuDismissed,
        onListingTypeSelected = viewModel::onListingTypeChanged,
        onCommunityNameClicked = viewModel::onCommunityNameClicked,
    )
}
