package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {

    private val searchParameters = MutableStateFlow(
        buildPostSearchParameters(
            sortType = SortType.ACTIVE,
            listingType = ListingType.ALL,
        ),
    )

    val posts
        get() = getPagingData.invoke(searchParameters.value).cachedIn(viewModelScope)

    private val _currentTime: MutableStateFlow<Instant> = MutableStateFlow(clock.instant())
    val currentTime = _currentTime.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    private val _listingType: MutableStateFlow<ListingType> = MutableStateFlow(ListingType.ALL)
    val listingType = _listingType.asStateFlow()

    init {
        viewModelScope.launch {
            initializeClock()
        }
    }

    suspend fun onListingTypeChanged(listingType: ListingType) {
        _listingType.emit(listingType)
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
