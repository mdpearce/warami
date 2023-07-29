package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.count
import com.neaniesoft.warami.common.models.startsWith
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.CommentsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommentsUseCase
    @Inject
    constructor(
        private val commentsRepository: CommentsRepository,
        private val ioDispatcher: IODispatcher,
    ) {

        suspend operator fun invoke(
            searchParameters: CommentSearchParameters,
            pageNumber: PageNumber,
        ): List<Pair<Comment, Int>> {
            return withContext(ioDispatcher) {
                val comments = commentsRepository.getComments(searchParameters, pageNumber)
                createCommentTree(
                    comments,
                )
            }
        }

        private fun createCommentTree(comments: List<Comment>): List<Pair<Comment, Int>> {
            val output = mutableListOf<Pair<Comment, Int>>()

            // Identify all root comments
            val minDepth = comments.minOf { it.path.count { char -> char == '.' } }

            val rootComments = comments.filter { it.path.count { char -> char == '.' } == minDepth }

            for (root in rootComments) {
                output.add(root to 0) // Add root comments with a depth of 0

                val childrenComments = comments.filter { it != root && it.path.startsWith(root.path.value) }
                output.addAll(createCommentTree(childrenComments, root, 1)) // Recursively add child comments
            }

            return output
        }

        private fun createCommentTree(comments: List<Comment>, parent: Comment, parentDepth: Int): List<Pair<Comment, Int>> {
            val output = mutableListOf<Pair<Comment, Int>>()

            for (comment in comments) {
                if (comment.path.startsWith(parent.path.value) && comment.path.count { char -> char == '.' } == parent.path.count { char -> char == '.' } + 1) {
                    output.add(comment to parentDepth)
                    output.addAll(createCommentTree(comments.filter { it != comment }, comment, parentDepth + 1))
                }
            }

            return output
        }
    }
