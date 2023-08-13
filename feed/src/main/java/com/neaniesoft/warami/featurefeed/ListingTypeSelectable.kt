package com.neaniesoft.warami.featurefeed

import com.neaniesoft.warami.common.models.ListingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ListingTypeSelectable {
    val listingType: Flow<ListingType>
    val listingTypeMenuItems: StateFlow<List<ListingTypeMenuItem>>
    fun onListingTypeChanged(listingType: ListingType)
    fun onListingTypeButtonClicked()
    fun onListingTypeMenuDismissed()
}

