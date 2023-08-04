package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
        private val clock: Clock,
        private val feedNavigator: FeedNavigator,
        private val getPagingData: GetPagingDataForPostsUseCase,
        private val isLoggedIn: IsLoggedInUseCase,
    ) : ViewModel() {

        private val searchParameters = MutableStateFlow(
            buildPostSearchParameters(
                sortType = SortType.ACTIVE,
                listingType = ListingType.ALL,
            ),
        )

        val postsFlow: Flow<PagingData<Post>> = searchParameters.flatMapLatest { params ->
            getPagingData(params)
        }.cachedIn(viewModelScope)

        private val _currentTime: MutableStateFlow<Instant> = MutableStateFlow(clock.instant())
        val currentTime = _currentTime.asStateFlow()

        private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
        val navigation = _navigation.asSharedFlow()

        private val _listingType: MutableStateFlow<ListingType> = MutableStateFlow(ListingType.ALL)
        val listingType = _listingType.asStateFlow()

        private val _listingTypeMenuItems: MutableStateFlow<List<ListingTypeMenuItem>> = MutableStateFlow(emptyList())
        val listingTypeMenuItems = _listingTypeMenuItems.asStateFlow()

        init {
            viewModelScope.launch {
                initializeClock()
            }
        }

        fun onListingTypeChanged(listingType: ListingType) {
            viewModelScope.launch {
                _listingType.emit(listingType)
                _listingTypeMenuItems.emit(emptyList())
                searchParameters.value = searchParameters.value.copy(listingType = listingType)
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
    }

data class ListingTypeMenuItem(val listingType: ListingType, val isEnabled: Boolean)
