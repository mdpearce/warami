package com.neaniesoft.warami.common.models

import java.time.LocalDateTime
import java.time.ZonedDateTime

data class Post(
    val postId: PostId,
    val sortIndex: SortIndex,
    val pageNum: PageNumber,
    val insertedAt: ZonedDateTime,
    val name: String,
    val creator: Person,
    val community: Community,
    val isRemoved: Boolean,
    val isLocked: Boolean,
    val publishedAt: LocalDateTime,
    val isDeleted: Boolean,
    val isNsfw: Boolean,
    val apId: String,
    val isLocal: Boolean,
    val languageId: Int,
    val isFeaturedCommunity: Boolean,
    val url: UriString?,
    val body: String?,
    val updatedAt: LocalDateTime?,
    val embedTitle: String?,
    val embedDescription: String?,
    val thumbnail: UriString?,
    val embedVideo: UriString?,
    val isCreatorBannedFromCommunity: Boolean,
    val aggregates: PostAggregates,
    val subscribedTyped: SubscribedType,
    val isSaved: Boolean,
    val isRead: Boolean,
    val isCreatorBlocked: Boolean,
    val myVote: Int?,
    val searchParameters: PostSearchParameters,
)

@JvmInline
value class SortIndex(val value: Int)

operator fun SortIndex.plus(other: Int): SortIndex = SortIndex(value + other)

operator fun SortIndex.plus(other: SortIndex): SortIndex = SortIndex(value + other.value)

operator fun SortIndex.minus(other: Int): SortIndex = SortIndex(value - other)

operator fun SortIndex.minus(other: SortIndex): SortIndex = SortIndex(value - other.value)

@JvmInline
value class PageNumber(val value: Int)

operator fun PageNumber.plus(other: Int): PageNumber {
    return PageNumber(value + other)
}

operator fun PageNumber.minus(other: Int): PageNumber {
    return PageNumber(value - other)
}
 operator fun PageNumber.compareTo(other: Int): Int {
     return value.compareTo(other)
 }
