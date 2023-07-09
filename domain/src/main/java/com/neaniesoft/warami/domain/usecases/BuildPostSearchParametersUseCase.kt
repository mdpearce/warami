package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainSortType
import com.neaniesoft.warami.domain.di.DomainScope
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class BuildPostSearchParametersUseCase {
    operator fun invoke(
        listingType: DomainListingType? = null,
        sortType: DomainSortType? = null,
        communityId: CommunityId? = null,
        communityName: String? = null,
        isSavedOnly: Boolean? = null,
    ): PostSearchParameters {
        return PostSearchParameters(
            listingType,
            sortType,
            communityId,
            communityName,
            isSavedOnly,
        )
    }
}
