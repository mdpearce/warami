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
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.data.repositories.settings.UserSettingsRepository
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject
constructor(
    userSettingsRepository: UserSettingsRepository,
    private val feedNavigator: FeedNavigator,
    private val getPagingData: GetPagingDataForPostsUseCase,
    private val listingTypeSelectable: ListingTypeSelectable,
    private val sortTypeSelectable: SortTypeSelectable,
) : ViewModel(), ListingTypeSelectable by listingTypeSelectable, SortTypeSelectable by sortTypeSelectable {

    private val searchParameters = MutableStateFlow(
        PostSearchParameters(null, null, null, null, null),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val postsFlow: Flow<PagingData<Post>> = userSettingsRepository.feedListingType()
        .combine(searchParameters) { listingType, searchParameters ->
            searchParameters.copy(listingType = listingType)
        }.combine(sortType) { params, sortType ->
            params.copy(sortType = sortType)
        }.flatMapLatest { params ->
            getPagingData(params)
        }.cachedIn(viewModelScope)

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    private val _uriNavigation: MutableSharedFlow<UriString?> = MutableSharedFlow()
    val uriNavigation = _uriNavigation.asSharedFlow()

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
