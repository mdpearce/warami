package com.neaniesoft.warami.featurefeed.components.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
    listingType: ListingType,
    onListTypeClicked: () -> Unit,
    listingTypeMenuItems: List<ListingTypeMenuItem>,
    onDismissListingTypeMenu: () -> Unit,
    onListingTypeSelected: (ListingType) -> Unit,
) {
    BottomAppBar(tonalElevation = 8.dp, contentPadding = PaddingValues(start = 8.dp, end = 8.dp)) {
        Box {
            Button(onClick = { onListTypeClicked() }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = FeedIcons.rememberFilterList(),
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
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFeedBottomBar() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            FeedBottomBar(
                listingType = ListingType.ALL,
                onListTypeClicked = {},
                listingTypeMenuItems = emptyList(),
                onDismissListingTypeMenu = {},
                onListingTypeSelected = {},
            )
        }
    }
}
