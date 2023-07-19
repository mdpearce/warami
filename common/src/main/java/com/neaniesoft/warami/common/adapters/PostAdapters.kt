package com.neaniesoft.warami.common.adapters

import com.neaniesoft.warami.api.models.PostView
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.SubscribedType
import com.neaniesoft.warami.common.models.UriString
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun PostView.toDomain(searchParameters: PostSearchParameters, insertedAt: ZonedDateTime): Post {
    return Post(
        postId = PostId(post.id.intValueExact()),
        insertedAt = insertedAt,
        name = post.name,
        creator = creator.toDomain(),
        community = community.toDomain(),
        isRemoved = post.removed,
        isLocked = post.locked,
        publishedAt = LocalDateTime.parse(post.published, DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC")),
        isDeleted = post.deleted,
        isNsfw = post.nsfw,
        apId = post.apId,
        isLocal = post.local,
        languageId = post.languageId.intValueExact(),
        isFeaturedCommunity = post.featuredCommunity,
        url = post.url?.let { UriString(it) },
        body = post.body,
        updatedAt = post.updated?.let {
            LocalDateTime.parse(
                it,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            ).atZone(ZoneId.of("UTC"))
        },
        embedTitle = post.embedTitle,
        embedDescription = post.embedDescription,
        thumbnail = post.thumbnailUrl?.let { UriString(it) },
        embedVideo = post.embedVideoUrl?.let { UriString(it) },
        isCreatorBannedFromCommunity = creatorBannedFromCommunity,
        aggregates = counts.toDomain(),
        subscribedTyped = SubscribedType.parse(subscribed.value),
        isSaved = saved,
        isRead = read,
        isCreatorBlocked = creatorBlocked,
        myVote = myVote?.intValueExact(),
        searchParameters = searchParameters,
    )
}
