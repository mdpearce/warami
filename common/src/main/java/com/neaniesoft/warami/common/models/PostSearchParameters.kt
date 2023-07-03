package com.neaniesoft.warami.common.models

import com.github.f4b6a3.ulid.Ulid

data class PostSearchParameters(
    val id: Ulid,
    val listingType: ListingType?,
    val sortType: SortType?,
    val pageNumber: Int?,
    val postLimit: Int?,
    val communityId: CommunityId?,
    val communityName: String?,
    val isSavedOnly: Boolean?
)
