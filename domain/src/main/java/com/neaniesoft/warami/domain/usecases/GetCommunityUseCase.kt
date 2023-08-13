package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.Community
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.data.repositories.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
) {
    operator fun invoke(communityId: CommunityId): Flow<Community> = communityRepository.getCommunity(communityId)
}
