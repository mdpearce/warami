package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.CommentSortType
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class BuildCommentSearchParametersUseCase {

    companion object {
        private const val MAX_DEPTH = 10
    }

    operator fun invoke(postId: PostId): CommentSearchParameters {
        return CommentSearchParameters(
            listingType = ListingType.ALL,
            commentSortType = CommentSortType.TOP,
            maxDepth = MAX_DEPTH,
            postId = postId,
            communityId = null,
            communityName = null,
            isSavedOnly = null,
            parentId = null,
        )
    }
}
