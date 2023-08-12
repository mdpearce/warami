package com.neaniesoft.warami.featurefeed.components.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.featurefeed.ListingTypeMenuItem
import com.neaniesoft.warami.featurefeed.R
import com.neaniesoft.warami.featurefeed.components.icons.FeedIcons

@Composable
fun FeedBottomBar(
    listingParams: FeedBottomBarListingParams?,
) {
    BottomAppBar(tonalElevation = 8.dp, contentPadding = PaddingValues(start = 8.dp, end = 8.dp)) {
        listingParams?.let { params ->
            with(params) {
                Box {
                    Button(onClick = { onListTypeClicked() }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = when (listingType) {
                                ListingType.ALL -> FeedIcons.rememberAllInclusive()
                                ListingType.SUBSCRIBED -> FeedIcons.rememberChecklist()
                                ListingType.LOCAL -> FeedIcons.rememberHomePin()
                            },
                            contentDescription = stringResource(id = R.string.content_description_list_type),
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(
                                id = when (listingType) {
                                    ListingType.ALL -> {
                                        R.string.listing_type_all
                                    }

                                    ListingType.LOCAL,
                                    -> {
                                        R.string.listing_type_local
                                    }

                                    ListingType.SUBSCRIBED,
                                    -> {
                                        R.string.listing_type_subscribed
                                    }
                                },
                            ),
                        )
                    }

                    DropdownMenu(expanded = listingTypeMenuItems.isNotEmpty(), onDismissRequest = onDismissListingTypeMenu) {
                        listingTypeMenuItems.forEach { menuItem ->
                            DropdownMenuItem(
                                enabled = menuItem.isEnabled,
                                text = {
                                    Text(
                                        text = stringResource(
                                            id = when (menuItem.listingType) {
                                                ListingType.ALL -> R.string.listing_type_all
                                                ListingType.LOCAL -> R.string.listing_type_local
                                                ListingType.SUBSCRIBED -> R.string.listing_type_subscribed
                                            },
                                        ),
                                    )
                                },
                                onClick = { onListingTypeSelected(menuItem.listingType) },
                                leadingIcon = {
                                    when (menuItem.listingType) {
                                        ListingType.ALL -> {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = FeedIcons.rememberAllInclusive(),
                                                contentDescription = stringResource(id = R.string.content_description_listing_type_all),
                                            )
                                        }

                                        ListingType.SUBSCRIBED -> {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = FeedIcons.rememberChecklist(),
                                                contentDescription = stringResource(id = R.string.content_description_listing_type_subscribed),
                                            )
                                        }

                                        ListingType.LOCAL -> {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = FeedIcons.rememberHomePin(),
                                                contentDescription = stringResource(id = R.string.content_description_listing_type_local),
                                            )
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

data class FeedBottomBarListingParams(
    val listingType: ListingType,
    val onListTypeClicked: () -> Unit,
    val listingTypeMenuItems: List<ListingTypeMenuItem>,
    val onDismissListingTypeMenu: () -> Unit,
    val onListingTypeSelected: (ListingType) -> Unit,
)

@Preview
@Composable
fun PreviewFeedBottomBar() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            FeedBottomBar(
                FeedBottomBarListingParams(
                    listingType = ListingType.ALL,
                    onListTypeClicked = {},
                    listingTypeMenuItems = emptyList(),
                    onDismissListingTypeMenu = {},
                    onListingTypeSelected = {},
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewFeedBottomBarListingTypeExpanded() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            FeedBottomBar(
                FeedBottomBarListingParams(
                    listingType = ListingType.ALL,
                    onListTypeClicked = {},
                    listingTypeMenuItems = ListingType.values().map { ListingTypeMenuItem(it, true) },
                    onDismissListingTypeMenu = {},
                    onListingTypeSelected = {},
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewFeedBottomBarListingTypeExpandedDisabledItem() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            FeedBottomBar(
                FeedBottomBarListingParams(
                    listingType = ListingType.ALL,
                    onListTypeClicked = {},
                    listingTypeMenuItems = ListingType.values().map { ListingTypeMenuItem(it, it != ListingType.SUBSCRIBED) },
                    onDismissListingTypeMenu = {},
                    onListingTypeSelected = {},
                ),
            )
        }
    }
}
