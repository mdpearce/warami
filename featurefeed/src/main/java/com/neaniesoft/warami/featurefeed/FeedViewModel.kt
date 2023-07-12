package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPagingDataForPostsUseCase
import com.neaniesoft.warami.featurefeed.di.FeedScope
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Inject

@Inject
@FeedScope
class FeedViewModel(
    private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
    private val getPagingData: GetPagingDataForPostsUseCase,
) : ViewModel() {

    private val searchParameters = MutableStateFlow(
        buildPostSearchParameters(
            sortType = SortType.ACTIVE,
            listingType = ListingType.ALL,
        ),
    )

    val posts = getPagingData.invoke(searchParameters.value).cachedIn(viewModelScope)


    // UI events
    fun onRefresh() {
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
}
