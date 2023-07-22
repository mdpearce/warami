package com.neaniesoft.warami.common.models

import java.time.ZonedDateTime

data class Comment(
    val commentId: CommentId,
    val creator: Person,
    val postId: PostId,
    val communityId: CommunityId,
    val content: String,
    val isRemoved: Boolean,
    val publishedAt: ZonedDateTime,
    val isDeleted: Boolean,
    val apId: ApId,
    val isLocal: Boolean,
    val path: CommentPath,
    val isDistinguished: Boolean,
    val languageId: LanguageId,
    val updatedAt: ZonedDateTime?,
    val counts: CommentAggregates,
    val isCreatorBannedFromCommunity: Boolean,
    val subscribedType: SubscribedType,
    val isSaved: Boolean,
    val isCreatorBlocked: Boolean,
    val myVote: Vote?,
)

data class CommentAggregates(
    val score: Score,
    val votes: Votes,
    val childCount: ChildCount,
    val hotRank: HotRank,
)

@JvmInline
value class CommentId(val value: Int)

@JvmInline
value class CreatorId(val value: Int)

@JvmInline
value class ApId(val value: String)

@JvmInline
value class CommentPath(val value: String)

@JvmInline
value class LanguageId(val value: Int)

@JvmInline
value class Score(val value: Int)

@JvmInline
value class ChildCount(val value: Int)

@JvmInline
value class HotRank(val value: Int)

@JvmInline
value class Vote(val value: Int)
