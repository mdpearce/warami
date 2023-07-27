package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentPath
import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.CommentsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository,
    private val ioDispatcher: IODispatcher,
) {

    suspend operator fun invoke(
        searchParameters: CommentSearchParameters,
        pageNumber: PageNumber,
    ): List<Pair<Comment, Int>> {
        return withContext(ioDispatcher) {
            val comments = commentsRepository.getComments(searchParameters, pageNumber)
            createCommentTree(comments)
        }
    }

    private fun createCommentTree(
        comments: List<Comment>,
        parentPath: CommentPath = CommentPath("0"),
        depth: Int = 0,
    ): List<Pair<Comment, Int>> {
        val childComments =
            comments.filter { it.path.value.startsWith("${parentPath.value}.") && it.path.value.count { ch -> ch == '.' } == parentPath.value.count { ch -> ch == '.' } + 1 }
        if (childComments.isEmpty()) {
            return emptyList()
        }

        return childComments.flatMap { comment ->
            listOf(comment to depth) + createCommentTree(comments, comment.path, depth + 1)
        }
    }
}
