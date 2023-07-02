package com.neaniesoft.warami.common.models

data class PostSearchParameters(
    val id: Int = 0,
    val listingType: ListingType?,
    val sortType: SortType?,
    val pageNumber: Int?,
    val postLimit: Int?,
    val communityId: CommunityId?,
    val communityName: String?,
    val isSavedOnly: Boolean?
)
