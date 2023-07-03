package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.repositories.post.PostListResult
import com.neaniesoft.warami.data.repositories.post.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForSearchParamsUseCase(private val postRepository: PostRepository) {
    operator fun invoke(searchParameters: PostSearchParameters): Flow<PostListResult> {
        return postRepository.listPostsFlow(searchParameters, true)
    }
}
