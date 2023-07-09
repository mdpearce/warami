package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.Resource
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPostsForSearchParamsUseCase
import com.neaniesoft.warami.featurefeed.di.FeedScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@FeedScope
class FeedViewModel(
    private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
    private val getPostsForSearchParams: GetPostsForSearchParamsUseCase,
) : ViewModel() {

    private var currentPage = 0
    private var sortIndex = 0
    private val searchParameters = MutableStateFlow(
        buildPostSearchParameters(
            sortType = SortType.ACTIVE,
            listingType = ListingType.ALL
        )
    )

    private val _posts = MutableStateFlow<Resource<List<Post>>>(Resource.Loading())
    val posts = _posts.asStateFlow()

    private fun fetchPosts() {
        searchParameters.value = buildPostSearchParameters()
        viewModelScope.launch {
            _posts.value = Resource.Loading()
            getPostsForSearchParams(
                ++currentPage,
                SortIndex(sortIndex),
                searchParameters.value
            ).collect { resource ->
                if (resource is Resource.Success) {
                    sortIndex = resource.data.last().sortIndex.value
                }
                _posts.emit(resource)
            }
        }
    }

    // UI events
    fun onRefresh() {
        currentPage = 0
        sortIndex = 0
        fetchPosts()
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
            isSavedOnly
        )
    }
}
