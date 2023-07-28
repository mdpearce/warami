package com.neaniesoft.warami.featurefeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommentPath
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.PostId
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
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

    suspend fun refresh(postId: PostId, parentCommentId: CommentId?) {
        Log.d("CommentsViewModel", "refresh($postId), parent: $parentCommentId")
        withContext(ioDispatcher) {
            try {
                Log.d("CommentsViewModel", "fetching comments")
                val comments = getComments(buildCommentSearchParameters(postId, parentCommentId), PageNumber(1))
                Log.d("CommentsViewModel", "Emitting comments")
                _comments.emit(comments)
            } catch (e: CommentsRepositoryException) {
                Log.d("CommentsViewMode", "Error: $e")
                _error.emit(e)
            }
        }
    }

    suspend fun initialFetch(postId: PostId, parentCommentId: CommentId?) {
        _comments.emit(emptyList())
        refresh(postId, parentCommentId)
    }

    fun onLoadMoreCommentsClicked(postId: PostId, commentId: CommentId) {
        Log.d("CommentsViewModel", "More comments clicked: $commentId")
        viewModelScope.launch {
            _navigation.emit(feedNavigator.commentsScreen(postId, commentId))
        }
    }
}
