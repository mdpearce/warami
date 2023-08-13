package com.neaniesoft.warami.featurefeed

import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.data.repositories.settings.UserSettingsRepository
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListingTypeSelectableImpl(
    private val userSettingsRepository: UserSettingsRepository,
    private val isLoggedIn: IsLoggedInUseCase,
) : ListingTypeSelectable {
    private val scope = CoroutineScope(Dispatchers.Default)

    override val listingType: Flow<ListingType> = userSettingsRepository.feedListingType()

    private val _listingTypeMenuItems: MutableStateFlow<List<ListingTypeMenuItem>> =
        MutableStateFlow(emptyList())
    override val listingTypeMenuItems: StateFlow<List<ListingTypeMenuItem>> = _listingTypeMenuItems.asStateFlow()

    override fun onListingTypeChanged(listingType: ListingType) {
        scope.launch {
            _listingTypeMenuItems.emit(emptyList()) // Dismiss dialog
            userSettingsRepository.setFeedListingType(listingType)
        }
    }

    override fun onListingTypeButtonClicked() {
        scope.launch {
            _listingTypeMenuItems.emit(
                ListingType.values().map {
                    when (it) {
                        ListingType.ALL, ListingType.LOCAL -> ListingTypeMenuItem(it, true)
                        ListingType.SUBSCRIBED -> ListingTypeMenuItem(it, isLoggedIn())
                    }
                },
            )
        }
    }

    override fun onListingTypeMenuDismissed() {
        scope.launch {
            _listingTypeMenuItems.emit(emptyList())
        }
    }
}
