package com.neaniesoft.warami.common.models

import java.math.BigInteger
import java.security.MessageDigest

data class CommentSearchParameters(
    val listingType: ListingType?,
    val commentSortType: CommentSortType?,
    val maxDepth: Int?,
    val communityId: CommunityId?,
    val communityName: String?,
    val postId: PostId?,
    val parentId: Int?,
    val isSavedOnly: Boolean?,
) {
    val id: String
        get() {
            val plainText =
                "${listingType?.value}:${commentSortType?.value}:$maxDepth:${communityId?.value}:$communityName:${postId?.value}:$parentId:$isSavedOnly"
            return sha256(plainText)
        }

    private fun sha256(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(64, '0')
    }
}

enum class CommentSortType(val value: String) {
    HOT("Hot"),
    TOP("Top"),
    NEW("New"),
    OLD("Old"),
}
