package com.neaniesoft.warami.common.models

import java.time.LocalDateTime

data class Post(
    val id: PostId,
    val sortIndex: SortIndex,
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
    val searchParameters: PostSearchParameters
)

@JvmInline
value class SortIndex(val value: Int)
