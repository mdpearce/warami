package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.Resource
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.data.repositories.post.PostRepository
import com.neaniesoft.warami.domain.di.DomainScope
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class GetPostsForSearchParamsUseCase(private val postRepository: PostRepository) {
    operator fun invoke(
        page: Int,
        initialSortIndex: SortIndex,
        searchParameters: PostSearchParameters
    ): Flow<Resource<List<Post>>> {
        return postRepository.fetchItems(page, initialSortIndex.value, searchParameters)
    }
}
