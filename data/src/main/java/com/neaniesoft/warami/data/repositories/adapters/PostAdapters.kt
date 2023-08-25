package com.neaniesoft.warami.data.repositories.adapters

import com.neaniesoft.warami.common.extensions.parseLocalDateTime
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toBoolean
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.Community
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.Person
import com.neaniesoft.warami.common.models.PersonId
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostAggregates
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.common.models.SubscribedType
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.models.Votes
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.db.SelectPostForPostIdByInsertTime
import com.neaniesoft.warami.data.repositories.DomainPost
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun SelectPostForPostIdByInsertTime.toDomain(): Post {
    val community = Community(
        id = CommunityId(communityId.toInt()),
        name = communityName,
        title = communityTitle,
        isRemoved = communityIsRemoved.toBoolean(),
        publishedAt = communityPublished.parseZonedDateTime(),
        isDeleted = communityIsDeleted.toBoolean(),
        isNsfw = communityIsNsfw.toBoolean(),
        actorId = ActorId(communityActorId),
        isLocal = communityIsLocal.toBoolean(),
        isHidden = communityIsHidden.toBoolean(),
        isPostingRestrictedToMods = communityIsPostingRestrictedToMods.toBoolean(),
        instanceId = InstanceId(communityInstanceId.toInt()),
        description = communityDescription,
        updatedAt = communityUpdatedAt?.parseZonedDateTime(),
        icon = communityIconUrl?.let { UriString(it) },
        banner = communityBannerUrl?.let { UriString(it) },
    )
    val creator = Person(
        id = PersonId(creatorId.toInt()),
        name = creatorName,
        isBanned = creatorIsBanned.toBoolean(),
        publishedAt = creatorPublishedAt.parseZonedDateTime(),
        actorId = ActorId(creatorActorId),
        isLocal = creatorIsLocal.toBoolean(),
        isDeleted = creatorIsDeleted.toBoolean(),
        isAdmin = creatorIsAdmin.toBoolean(),
        isBotAccount = creatorIsBotAccount.toBoolean(),
        instanceId = InstanceId(creatorInstanceId.toInt()),
        displayName = creatorDisplayName,
        avatarUrl = creatorAvatarUrl?.let { UriString(it) },
        updatedAt = creatorUpdatedAt?.parseZonedDateTime(),
        bio = creatorBio,
        bannerUrl = creatorBannerUrl?.let { UriString(it) },
        matrixUserId = creatorMatrixUserId,
        banExpiresAt = creatorBanExpires?.parseZonedDateTime(),
    )
    val counts = PostAggregates(
        id = aggregates.toInt(),
        postId = PostId(postId.toInt()),
        commentCount = countsComments.toInt(),
        score = countsScore.toInt(),
        votes = Votes(up = countsUpVotes.toInt(), down = countsDownVotes.toInt()),
        publishedAt = countsPublishedAt.parseZonedDateTime(),
        newestCommentTimeNecro = countsNewestCommentTime.parseZonedDateTime(),
        newestComment = countsNewestCommentTime.parseZonedDateTime(),
        isFeaturedCommunity = countsIsFeaturedCommunity.toBoolean(),
        isFeaturedLocal = countsIsFeaturedLocal.toBoolean(),
        hotRank = countsHotRank.toInt(),
        hotRankActive = countsHotRankActive.toInt(),
    )

    return Post(
        postId = PostId(postId.toInt()),
        insertedAt = insertedAt.parseZonedDateTime(),
        name = name,
        creator = creator,
        community = community,
        isRemoved = isRemoved.toBoolean(),
        isLocked = isLocked.toBoolean(),
        publishedAt = publishedAt.parseZonedDateTime(),
        isDeleted = isDeleted.toBoolean(),
        isNsfw = isNsfw.toBoolean(),
        apId = apId,
        isLocal = isLocal.toBoolean(),
        languageId = languageId.toInt(),
        isFeaturedCommunity = isFeaturedCommunity.toBoolean(),
        url = url?.let { UriString(it) },
        body = body,
        updatedAt = updatedAt?.parseZonedDateTime(),
        embedTitle = embedTitle,
        embedDescription = embedDescription,
        thumbnail = thumbnailUrl?.let { UriString(it) },
        embedVideo = embedVideoUrl?.let { UriString(it) },
        isCreatorBannedFromCommunity = isCreatorBannedFromCommunity.toBoolean(),
        aggregates = counts,
        subscribedTyped = SubscribedType.parse(subscribedType),
        isSaved = isSaved.toBoolean(),
        isRead = isRead.toBoolean(),
        isCreatorBlocked = isCreatorBlocked.toBoolean(),
        myVote = myVote?.toInt(),
        searchParameters = PostSearchParameters(null, null, null, null, null),
    )
}

fun SelectBySearchParams.toDomain(
    dateTimeFormatter: DateTimeFormatter,
    searchParameters: PostSearchParameters,
): Post {
    fun String.parseLocalDateTime(): LocalDateTime = this.parseLocalDateTime(dateTimeFormatter)

    val community = Community(
        id = CommunityId(communityId.toInt()),
        name = communityName,
        title = communityTitle,
        isRemoved = communityIsRemoved.toBoolean(),
        publishedAt = communityPublished.parseZonedDateTime(),
        isDeleted = communityIsDeleted.toBoolean(),
        isNsfw = communityIsNsfw.toBoolean(),
        actorId = ActorId(communityActorId),
        isLocal = communityIsLocal.toBoolean(),
        isHidden = communityIsHidden.toBoolean(),
        isPostingRestrictedToMods = communityIsPostingRestrictedToMods.toBoolean(),
        instanceId = InstanceId(communityInstanceId.toInt()),
        description = communityDescription,
        updatedAt = communityUpdatedAt?.parseZonedDateTime(),
        icon = communityIconUrl?.let { UriString(it) },
        banner = communityBannerUrl?.let { UriString(it) },
    )
    val creator = Person(
        id = PersonId(creatorId.toInt()),
        name = creatorName,
        isBanned = creatorIsBanned.toBoolean(),
        publishedAt = creatorPublishedAt.parseZonedDateTime(),
        actorId = ActorId(creatorActorId),
        isLocal = creatorIsLocal.toBoolean(),
        isDeleted = creatorIsDeleted.toBoolean(),
        isAdmin = creatorIsAdmin.toBoolean(),
        isBotAccount = creatorIsBotAccount.toBoolean(),
        instanceId = InstanceId(creatorInstanceId.toInt()),
        displayName = creatorDisplayName,
        avatarUrl = creatorAvatarUrl?.let { UriString(it) },
        updatedAt = creatorUpdatedAt?.parseZonedDateTime(),
        bio = creatorBio,
        bannerUrl = creatorBannerUrl?.let { UriString(it) },
        matrixUserId = creatorMatrixUserId,
        banExpiresAt = creatorBanExpires?.parseZonedDateTime(),
    )
    val counts = PostAggregates(
        id = aggregates.toInt(),
        postId = PostId(postId.toInt()),
        commentCount = countsComments.toInt(),
        score = countsScore.toInt(),
        votes = Votes(up = countsUpVotes.toInt(), down = countsDownVotes.toInt()),
        publishedAt = countsPublishedAt.parseZonedDateTime(),
        newestCommentTimeNecro = countsNewestCommentTime.parseZonedDateTime(),
        newestComment = countsNewestCommentTime.parseZonedDateTime(),
        isFeaturedCommunity = countsIsFeaturedCommunity.toBoolean(),
        isFeaturedLocal = countsIsFeaturedLocal.toBoolean(),
        hotRank = countsHotRank.toInt(),
        hotRankActive = countsHotRankActive.toInt(),
    )

    return Post(
        postId = PostId(postId.toInt()),
        insertedAt = insertedAt.parseZonedDateTime(),
        name = name,
        creator = creator,
        community = community,
        isRemoved = isRemoved.toBoolean(),
        isLocked = isLocked.toBoolean(),
        publishedAt = publishedAt.parseZonedDateTime(),
        isDeleted = isDeleted.toBoolean(),
        isNsfw = isNsfw.toBoolean(),
        apId = apId,
        isLocal = isLocal.toBoolean(),
        languageId = languageId.toInt(),
        isFeaturedCommunity = isFeaturedCommunity.toBoolean(),
        url = url?.let { UriString(it) },
        body = body,
        updatedAt = updatedAt?.parseZonedDateTime(),
        embedTitle = embedTitle,
        embedDescription = embedDescription,
        thumbnail = thumbnailUrl?.let { UriString(it) },
        embedVideo = embedVideoUrl?.let { UriString(it) },
        isCreatorBannedFromCommunity = isCreatorBannedFromCommunity.toBoolean(),
        aggregates = counts,
        subscribedTyped = SubscribedType.parse(subscribedType),
        isSaved = isSaved.toBoolean(),
        isRead = isRead.toBoolean(),
        isCreatorBlocked = isCreatorBlocked.toBoolean(),
        myVote = myVote?.toInt(),
        searchParameters = searchParameters,
    )
}

typealias DbPost = com.neaniesoft.warami.data.db.Post

fun DomainPost.toDb(): DbPost {
    return DbPost(
        id = 0,
        postId = postId.value.toLong(),
        insertedAt = insertedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
        name = name,
        creatorId = creator.id.value.toLong(),
        communityId = community.id.value.toLong(),
        isRemoved = isRemoved.toLong(),
        isLocked = isLocked.toLong(),
        publishedAt = publishedAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
        isDeleted = isDeleted.toLong(),
        isNsfw = isNsfw.toLong(),
        apId = apId,
        isLocal = isLocal.toLong(),
        languageId = languageId.toLong(),
        isFeaturedCommunity = isFeaturedCommunity.toLong(),
        url = url?.value,
        body = body,
        updatedAt = updatedAt?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
        embedTitle = embedTitle,
        embedDescription = embedDescription,
        thumbnailUrl = thumbnail?.value,
        embedVideoUrl = embedVideo?.value,
        isCreatorBannedFromCommunity = isCreatorBannedFromCommunity.toLong(),
        aggregates = aggregates.id.toLong(),
        subscribedType = subscribedTyped.value,
        isSaved = isSaved.toLong(),
        isRead = isRead.toLong(),
        isCreatorBlocked = isCreatorBlocked.toLong(),
        myVote = myVote?.toLong(),
        searchParams = searchParameters.id,
    )
}
