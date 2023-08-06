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
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.data.repositories.CommunityRepository
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject
constructor(
    private val clock: Clock,
    private val feedNavigator: FeedNavigator,
    private val getPagingData: GetPagingDataForPostsUseCase,
    private val isLoggedIn: IsLoggedInUseCase,
    private val userSettingsRepository: UserSettingsRepository,
    private val communityRepository: CommunityRepository,
) : ViewModel() {

    private val searchParameters = MutableStateFlow(
        PostSearchParameters(null, SortType.ACTIVE, null, null, null),
    )

    private val communityId: MutableStateFlow<CommunityId?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val postsFlow: Flow<PagingData<Post>> = userSettingsRepository.feedListingType()
        .combine(searchParameters) { listingType, searchParameters ->
            searchParameters.copy(listingType = listingType)
        }.combine(communityId) { searchParams, communityId ->
            searchParams.copy(communityId = communityId)
        }.flatMapLatest { params ->
            getPagingData(params)
        }.cachedIn(viewModelScope)

    private val _currentTime: MutableStateFlow<Instant> = MutableStateFlow(clock.instant())
    val currentTime = _currentTime.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    val listingType = userSettingsRepository.feedListingType()

    private val _listingTypeMenuItems: MutableStateFlow<List<ListingTypeMenuItem>> = MutableStateFlow(emptyList())
    val listingTypeMenuItems = _listingTypeMenuItems.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val communityName: Flow<String> = communityId.filterNotNull()
        .flatMapLatest { id ->
            communityRepository.getCommunity(id)
        }.map { it.title }

    init {
        viewModelScope.launch {
            initializeClock()
        }
    }

    suspend fun onCommunityId(communityId: CommunityId?) {
        this.communityId.emit(communityId)
    }

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

    private suspend fun initializeClock() {
        _currentTime.emit(clock.instant())
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
}

data class ListingTypeMenuItem(val listingType: ListingType, val isEnabled: Boolean)
