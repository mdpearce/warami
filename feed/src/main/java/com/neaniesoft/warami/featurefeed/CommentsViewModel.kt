package com.neaniesoft.warami.featurefeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.CommentsRepositoryException
import com.neaniesoft.warami.domain.usecases.BuildCommentSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetCommentsUseCase
import com.neaniesoft.warami.featurefeed.di.FeedScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject

@Inject
@FeedScope
class CommentsViewModel(
    private val buildCommentSearchParameters: BuildCommentSearchParametersUseCase,
    private val getComments: GetCommentsUseCase,
    private val ioDispatcher: IODispatcher,
) : ViewModel() {

    private val _comments: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val comments = _comments.asStateFlow()

    private val _error: MutableSharedFlow<Exception?> = MutableSharedFlow()
    val error = _error.asSharedFlow()

    fun refresh(postId: PostId) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                try {
                    val comments = getComments(buildCommentSearchParameters(postId), PageNumber(1))
                    _comments.emit(comments)
                } catch (e: CommentsRepositoryException) {
                    _error.emit(e)
                }
            }
        }
    }
}
