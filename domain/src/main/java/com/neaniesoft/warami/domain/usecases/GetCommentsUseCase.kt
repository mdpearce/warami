package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.data.repositories.CommentsRepository
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class GetCommentsUseCase(
    private val commentsRepository: CommentsRepository,
) {

    suspend operator fun invoke(searchParameters: CommentSearchParameters, pageNumber: PageNumber): List<Comment> {
        return commentsRepository.getComments(searchParameters, pageNumber)
    }
}
