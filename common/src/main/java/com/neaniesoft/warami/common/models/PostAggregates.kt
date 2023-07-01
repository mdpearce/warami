package com.neaniesoft.warami.common.models

import java.time.LocalDateTime

data class PostAggregates(
    val id: Int,
    val postId: PostId,
    val commentCount: Int,
    val score: Int,
    val votes: Votes,
    val publishedAt: LocalDateTime,
    val newestCommentTimeNecro: LocalDateTime,
    val newestComment: LocalDateTime,
    val isFeaturedCommunity: Boolean,
    val isFeaturedLocal: Boolean,
    val hotRank: Int,
    val hotRankActive: Int,
)
