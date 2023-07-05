package com.neaniesoft.warami.common.models

import java.math.BigInteger
import java.security.MessageDigest

data class PostSearchParameters(
    val listingType: ListingType?,
    val sortType: SortType?,
    val pageNumber: Int?,
    val postLimit: Int?,
    val communityId: CommunityId?,
    val communityName: String?,
    val isSavedOnly: Boolean?
) {
    val id: String
        get() {
            val plainText =
                "${listingType?.value}:${sortType?.value}:$pageNumber:$postLimit:${communityId?.value}:$communityName:$isSavedOnly"
            return sha256(plainText)
        }

    private fun sha256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(64, '0')
    }
}
