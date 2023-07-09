package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.Resource
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.models.split
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPostsForSearchParamsUseCase
import com.neaniesoft.warami.featurefeed.di.FeedScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
@FeedScope
class FeedViewModel(
    private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
    private val getPostsForSearchParams: GetPostsForSearchParamsUseCase,
) : ViewModel() {

    private var currentPage = 0
    private val searchParameters = MutableStateFlow(
        buildPostSearchParameters(
            sortType = SortType.ACTIVE,
            listingType = ListingType.ALL
        )
    )

    private val postResource = MutableStateFlow<Resource<List<Post>>>(Resource.Loading())

    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())
    val posts = _posts.asStateFlow()

    private val _loadingState: MutableStateFlow<Resource<List<Post>>> =
        MutableStateFlow(Resource.Loading())
    val loadingState = _loadingState.asStateFlow()

    init {
        postResource.split(_posts, _loadingState).launchIn(viewModelScope)
    }

    private fun fetchPosts(refresh: Boolean) {
        val sortIndex = if (refresh) {
            SortIndex(0)
        } else {
            posts.value.lastOrNull()?.sortIndex ?: SortIndex(0)
        }
        searchParameters.value = buildPostSearchParameters()
        viewModelScope.launch {
            getPostsForSearchParams(
                ++currentPage,
                sortIndex,
                searchParameters.value
            ).collect { resource ->
                postResource.value = resource
            }
        }
    }

    // UI events
    fun onRefresh() {
        currentPage = 0
        fetchPosts(true)
    }

    fun onLoadMorePosts() {
        fetchPosts(false)
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
