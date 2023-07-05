package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import javax.inject.Inject

class BuildPostSearchParametersUseCase @Inject constructor() {
    operator fun invoke(
        listingType: DomainListingType? = null,
        sortType: DomainSortType? = null,
        pageNumber: Int? = null,
        postLimit: Int? = null,
        communityId: CommunityId? = null,
        communityName: String? = null,
        isSavedOnly: Boolean? = null,
    ): PostSearchParameters {
        return PostSearchParameters(
            listingType, sortType, pageNumber, postLimit, communityId, communityName, isSavedOnly
        )
    }
}
