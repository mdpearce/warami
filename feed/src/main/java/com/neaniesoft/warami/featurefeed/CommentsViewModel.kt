package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.plus
import com.neaniesoft.warami.common.navigation.FeedNavigator
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.CommentsRepositoryException
import com.neaniesoft.warami.domain.usecases.BuildCommentSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetCommentsUseCase
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel
@Inject
constructor(
    private val buildCommentSearchParameters: BuildCommentSearchParametersUseCase,
    private val getComments: GetCommentsUseCase,
    private val feedNavigator: FeedNavigator,
    private val ioDispatcher: IODispatcher,
) : ViewModel() {

    private val _comments: MutableStateFlow<List<Pair<Comment, Int>>> = MutableStateFlow(emptyList())
    val comments = _comments.asStateFlow()

    private val _error: MutableSharedFlow<Exception?> = MutableSharedFlow()
    val error = _error.asSharedFlow()

    private val _navigation: MutableSharedFlow<Direction?> = MutableSharedFlow()
    val navigation = _navigation.asSharedFlow()

    private val pageNumber: MutableStateFlow<PageNumber> = MutableStateFlow(PageNumber(1))

    private val _pageLoadingContent: MutableStateFlow<PageLoadingContent> = MutableStateFlow(PageLoadingContent.MaybeMoreResults)
    val pageLoadingContent = _pageLoadingContent.asStateFlow()

    private val _isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refresh(postId: PostId, parentCommentId: CommentId?) {
        Timber.d("refresh($postId), parent: $parentCommentId")
        viewModelScope.launch(ioDispatcher) {
            _isRefreshing.emit(true)
            try {
                Timber.d("fetching comments")
                val comments = getComments(buildCommentSearchParameters(postId, parentCommentId), pageNumber.value)
                Timber.d("Emitting comments")
                _comments.emit(comments)
                _pageLoadingContent.emit(PageLoadingContent.MaybeMoreResults)
                pageNumber.value = pageNumber.value + 1
            } catch (e: CommentsRepositoryException) {
                Timber.e(e)
                _error.emit(e)
            } finally {
                _isRefreshing.emit(false)
            }
        }
    }

    suspend fun initialFetch(postId: PostId, parentCommentId: CommentId?) {
        _comments.emit(emptyList())
        refresh(postId, parentCommentId)
    }

    fun onLoadMoreCommentsClicked(postId: PostId, commentId: CommentId) {
        Timber.d("More comments clicked: $commentId")
        viewModelScope.launch {
            _navigation.emit(feedNavigator.commentsScreen(postId, commentId))
        }
    }

    fun onLoadNextPage(postId: PostId, parentCommentId: CommentId?) {
        viewModelScope.launch {
            _pageLoadingContent.emit(PageLoadingContent.LoadingNextPage)
            val currentCommentCount = comments.value.size
            val comments = getComments(buildCommentSearchParameters(postId, parentCommentId), pageNumber.value)
            _comments.emit(comments)
            if (comments.size == currentCommentCount) {
                _pageLoadingContent.emit(PageLoadingContent.NoResults)
            } else {
                _pageLoadingContent.emit(PageLoadingContent.MaybeMoreResults)
            }
        }
    }
}

sealed class PageLoadingContent {
    data object LoadingNextPage : PageLoadingContent()
    data object NoResults : PageLoadingContent()
    data object MaybeMoreResults : PageLoadingContent()
}
