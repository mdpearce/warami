package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.data.repositories.settings.UserSettingsRepository
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject
constructor(
    private val feedNavigator: FeedNavigator,
    private val getPagingData: GetPagingDataForPostsUseCase,
    private val isLoggedIn: IsLoggedInUseCase,
    private val userSettingsRepository: UserSettingsRepository,
) : ViewModel() {

    private val searchParameters = MutableStateFlow(
        PostSearchParameters(null, SortType.ACTIVE, null, null, null),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val postsFlow: Flow<PagingData<Post>> = userSettingsRepository.feedListingType()
        .combine(searchParameters) { listingType, searchParameters ->
            searchParameters.copy(listingType = listingType)
        }.flatMapLatest { params ->
            getPagingData(params)
        }.cachedIn(viewModelScope)

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    private val _uriNavigation: MutableSharedFlow<UriString?> = MutableSharedFlow()
    val uriNavigation = _uriNavigation.asSharedFlow()

    val listingType = userSettingsRepository.feedListingType()

    private val _listingTypeMenuItems: MutableStateFlow<List<ListingTypeMenuItem>> = MutableStateFlow(emptyList())
    val listingTypeMenuItems = _listingTypeMenuItems.asStateFlow()

    fun onListingTypeChanged(listingType: ListingType) {
        viewModelScope.launch {
            _listingTypeMenuItems.emit(emptyList()) // dismiss menu
            userSettingsRepository.setFeedListingType(listingType)
        }
    }

    fun onListingTypeButtonClicked() {
        viewModelScope.launch {
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

    fun onListingTypeMenuDismissed() {
        viewModelScope.launch {
            _listingTypeMenuItems.emit(emptyList())
        }
    }

    fun onPostClicked(postId: PostId) {
        viewModelScope.launch {
            _navigation.emit(feedNavigator.commentsScreen(postId))
        }
    }

    fun onCommunityNameClicked(communityId: CommunityId) {
        viewModelScope.launch {
            _navigation.emit(feedNavigator.feedScreenForCommunity(communityId))
        }
    }

    fun onLinkClicked(uri: UriString) {
        viewModelScope.launch {
            _uriNavigation.emit(uri)
        }
    }
}

data class ListingTypeMenuItem(val listingType: ListingType, val isEnabled: Boolean)
