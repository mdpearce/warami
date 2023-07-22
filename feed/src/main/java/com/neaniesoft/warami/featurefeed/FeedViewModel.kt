package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.neaniesoft.warami.featurefeed.di.FeedScope
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import java.time.Clock
import java.time.Instant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Inject
@FeedScope
class FeedViewModel(
    private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
    getPagingData: GetPagingDataForPostsUseCase,
    private val clock: Clock,
    private val feedNavigator: FeedNavigator,
) : ViewModel() {

    private val searchParameters = MutableStateFlow(
        buildPostSearchParameters(
            sortType = SortType.ACTIVE,
            listingType = ListingType.ALL,
        ),
    )

    val posts = getPagingData.invoke(searchParameters.value).cachedIn(viewModelScope)

    private val _currentTime: MutableStateFlow<Instant> = MutableStateFlow(clock.instant())
    val currentTime = _currentTime.asStateFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    init {
        viewModelScope.launch {
            initializeClock()
        }
    }

    fun onSearchParamsChanged(
        listingType: DomainListingType? = null,
        sortType: DomainSortType? = null,
        communityId: CommunityId? = null,
        communityName: String? = null,
        isSavedOnly: Boolean? = null,
    ) {
        searchParameters.value = buildPostSearchParameters(
            listingType,
            sortType,
            communityId,
            communityName,
            isSavedOnly,
        )
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
