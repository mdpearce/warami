package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.CommentSortType
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.PostId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildCommentSearchParametersUseCase
@Inject
constructor() {

    companion object {
        const val MAX_DEPTH = 8
    }

    operator fun invoke(postId: PostId, parentCommentId: CommentId?): CommentSearchParameters {
        return CommentSearchParameters(
            listingType = ListingType.ALL,
            commentSortType = CommentSortType.TOP,
            maxDepth = MAX_DEPTH,
            postId = postId,
            communityId = null,
            communityName = null,
            isSavedOnly = null,
            parentId = parentCommentId,
        )
    }
}
