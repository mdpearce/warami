package com.neaniesoft.warami.common.adapters

import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.Votes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun ApiAggregates.toDomain(): DomainAggregates {
    return DomainAggregates(
        id = id.intValueExact(),
        postId = PostId(postId.intValueExact()),
        commentCount = comments.intValueExact(),
        score = score.intValueExact(),
        votes = Votes(up = upvotes.intValueExact(), down = downvotes.intValueExact()),
        publishedAt = LocalDateTime.parse(published, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        newestCommentTimeNecro = LocalDateTime.parse(
            newestCommentTimeNecro,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
        newestComment = LocalDateTime.parse(
            newestCommentTime,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        ),
        isFeaturedCommunity = featuredCommunity,
        isFeaturedLocal = featuredLocal,
        hotRank = hotRank.intValueExact(),
        hotRankActive = hotRankActive.intValueExact(),
    )
}
