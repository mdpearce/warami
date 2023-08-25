package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.data.repositories.post.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    operator fun invoke(postId: PostId): Flow<Post> = postRepository.post(postId).mapNotNull { it }
}
