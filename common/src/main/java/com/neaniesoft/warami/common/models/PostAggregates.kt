package com.neaniesoft.warami.common.models

import java.time.LocalDateTime
import java.time.ZonedDateTime

data class PostAggregates(
    val id: Int,
    val postId: PostId,
    val commentCount: Int,
    val score: Int,
    val votes: Votes,
    val publishedAt: ZonedDateTime,
    val newestCommentTimeNecro: ZonedDateTime,
    val newestComment: ZonedDateTime,
    val isFeaturedCommunity: Boolean,
    val isFeaturedLocal: Boolean,
    val hotRank: Int,
    val hotRankActive: Int,
)
