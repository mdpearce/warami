package com.neaniesoft.warami.domain.usecases

import com.github.f4b6a3.ulid.UlidCreator
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType

class BuildPostSearchParametersUseCase {
    operator fun invoke(
        listingType: DomainListingType?,
        sortType: DomainSortType?,
        pageNumber: Int?,
        postLimit: Int?,
        communityId: CommunityId?,
        communityName: String?,
        isSavedOnly: Boolean?
    ): PostSearchParameters {
        return PostSearchParameters(
            id = UlidCreator.getMonotonicUlid(),
            listingType, sortType, pageNumber, postLimit, communityId, communityName, isSavedOnly
        )
    }
}
