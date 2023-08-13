package com.neaniesoft.warami.featurefeed

import android.content.Intent
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.featurefeed.components.feed.FeedBottomBar
import com.neaniesoft.warami.featurefeed.components.feed.FeedBottomBarSortTypeParams
import com.neaniesoft.warami.featurefeed.components.feed.FeedScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CommunityFeedScreen(
    navigator: DestinationsNavigator,
    communityId: CommunityId,
    viewModel: CommunityFeedViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()

    val navigation by viewModel.navigation.collectAsState(initial = null)

    val uriNavigation by viewModel.uriNavigation.collectAsState(initial = null)

    val posts = viewModel.postsFlow.collectAsLazyPagingItems()

    val community by viewModel.community.collectAsState(initial = null)

    val sortType by viewModel.sortType.collectAsState()

    val sortTypeMenuItems by viewModel.sortTypeMenuItems.collectAsState()

    LaunchedEffect(key1 = navigation) {
        val destination = navigation
        if (destination != null) {
            Timber.d("Navigating to " + destination.route)
            navigator.navigate(destination)
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = uriNavigation) {
        val uri = uriNavigation?.value?.toUri()
        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    LaunchedEffect(key1 = communityId) {
        viewModel.onCommunityId(communityId)
    }

    Timber.d("About to render screen content: $listState, $posts")

    FeedScreenContent(
        listState,
        posts,
        { viewModel.onPostClicked(it) },
        onCommunityNameClicked = viewModel::onCommunityNameClicked,
        onLinkClicked = viewModel::onLinkClicked,
        topBar = {
            community?.let { community ->
                TopAppBar(title = { Text(text = community.title, style = MaterialTheme.typography.titleMedium) })
            }
        },
        bottomBar = {
            FeedBottomBar(
                listingParams = null,
                sortTypeParams = FeedBottomBarSortTypeParams(
                    sortType = sortType,
                    onSortTypeClicked = viewModel::onSortTypeButtonClicked,
                    sortTypeMenuItems = sortTypeMenuItems,
                    onDismissSortTypeMenu = viewModel::onSortTypeMenuDismissed,
                    onSortTypeSelected = viewModel::onSortTypeChanged,
                ),
            )
        },
    )
}
