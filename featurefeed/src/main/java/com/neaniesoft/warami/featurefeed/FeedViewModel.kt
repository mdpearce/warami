package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.data.repositories.post.ErrorFetching
import com.neaniesoft.warami.data.repositories.post.Fetching
import com.neaniesoft.warami.data.repositories.post.Finished
import com.neaniesoft.warami.data.repositories.post.PostList
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPostsForSearchParamsUseCase
import com.neaniesoft.warami.featurefeed.models.EmptyFeed
import com.neaniesoft.warami.featurefeed.models.ErrorFeed
import com.neaniesoft.warami.featurefeed.models.FeedListContent
import com.neaniesoft.warami.featurefeed.models.NotRefreshing
import com.neaniesoft.warami.featurefeed.models.PostFeed
import com.neaniesoft.warami.featurefeed.models.Refreshing
import com.neaniesoft.warami.featurefeed.models.RefreshingIndicator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val buildPostSearchParameters: BuildPostSearchParametersUseCase,
    private val getPostsForSearchParams: GetPostsForSearchParamsUseCase
) : ViewModel() {

    // Flows for UI updates
    private val _feedList: MutableStateFlow<FeedListContent> = MutableStateFlow(EmptyFeed)
    val feedList: StateFlow<FeedListContent> = _feedList.asStateFlow()

    private val _refreshing: MutableStateFlow<RefreshingIndicator> = MutableStateFlow(NotRefreshing)
    val refreshing: StateFlow<RefreshingIndicator> = _refreshing.asStateFlow()

    // TODO: Remove temporary hard-coded search params used while testing
    private val searchParams = buildPostSearchParameters()

    private var refreshJob: Job? = null

    // UI events
    fun onRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            handleRefreshRequested()
        }
    }

    private suspend fun handleRefreshRequested() {
        getPostsForSearchParams(searchParams).collect { result ->
            when (result) {
                is ErrorFetching -> _feedList.emit(ErrorFeed(result.exception))
                Fetching -> _refreshing.emit(Refreshing)
                Finished -> _refreshing.emit(NotRefreshing)
                is PostList -> _feedList.emit(PostFeed(result.posts))
            }
        }
    }
}
