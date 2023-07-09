package com.neaniesoft.warami.data.repositories.adapters

import com.neaniesoft.warami.api.models.GetPosts
import com.neaniesoft.warami.common.extensions.parseLocalDateTime
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
import com.neaniesoft.warami.common.models.SortIndex
import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.common.models.SubscribedType
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.models.Votes
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.repositories.ApiListingType
import com.neaniesoft.warami.data.repositories.ApiSortType
import com.neaniesoft.warami.data.repositories.DomainListingType
import com.neaniesoft.warami.data.repositories.DomainPost
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        publishedAt = communityPublished.parseLocalDateTime(),
        isDeleted = communityIsDeleted.toBoolean(),
        isNsfw = communityIsNsfw.toBoolean(),
        actorId = ActorId(communityActorId),
        isLocal = communityIsLocal.toBoolean(),
        isHidden = communityIsHidden.toBoolean(),
        isPostingRestrictedToMods = communityIsPostingRestrictedToMods.toBoolean(),
        instanceId = InstanceId(communityInstanceId.toInt()),
        description = communityDescription,
        updatedAt = communityUpdatedAt?.parseLocalDateTime(),
        icon = communityIconUrl?.let { UriString(it) },
        banner = communityBannerUrl?.let { UriString(it) },
    )
    val creator = Person(
        id = PersonId(creatorId.toInt()),
        name = creatorName,
        isBanned = creatorIsBanned.toBoolean(),
        publishedAt = creatorPublishedAt.parseLocalDateTime(),
        actorId = ActorId(creatorActorId),
        isLocal = creatorIsLocal.toBoolean(),
        isDeleted = creatorIsDeleted.toBoolean(),
        isAdmin = creatorIsAdmin.toBoolean(),
        isBotAccount = creatorIsBotAccount.toBoolean(),
        instanceId = InstanceId(creatorInstanceId.toInt()),
        displayName = creatorDisplayName,
        avatarUrl = creatorAvatarUrl?.let { UriString(it) },
        updatedAt = creatorUpdatedAt?.parseLocalDateTime(),
        bio = creatorBio,
        bannerUrl = creatorBannerUrl?.let { UriString(it) },
        matrixUserId = creatorMatrixUserId,
        banExpiresAt = creatorBanExpires?.parseLocalDateTime(),
    )
    val counts = PostAggregates(
        id = aggregates.toInt(),
        postId = PostId(id.toInt()),
        commentCount = countsComments.toInt(),
        score = countsScore.toInt(),
        votes = Votes(up = countsUpVotes.toInt(), down = countsDownVotes.toInt()),
        publishedAt = countsPublishedAt.parseLocalDateTime(),
        newestCommentTimeNecro = countsNewestCommentTime.parseLocalDateTime(),
        newestComment = countsNewestCommentTime.parseLocalDateTime(),
        isFeaturedCommunity = countsIsFeaturedCommunity.toBoolean(),
        isFeaturedLocal = countsIsFeaturedLocal.toBoolean(),
        hotRank = countsHotRank.toInt(),
        hotRankActive = countsHotRankActive.toInt(),
    )

    return Post(
        id = PostId(id.toInt()),
        sortIndex = SortIndex(sortIndex.toInt()),
        name = name,
        creator = creator,
        community = community,
        isRemoved = isRemoved.toBoolean(),
        isLocked = isLocked.toBoolean(),
        publishedAt = publishedAt.parseLocalDateTime(),
        isDeleted = isDeleted.toBoolean(),
        isNsfw = isNsfw.toBoolean(),
        apId = apId,
        isLocal = isLocal.toBoolean(),
        languageId = languageId.toInt(),
        isFeaturedCommunity = isFeaturedCommunity.toBoolean(),
        url = url?.let { UriString(it) },
        body = body,
        updatedAt = updatedAt?.parseLocalDateTime(),
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

fun DomainPost.toDb(formatter: DateTimeFormatter): DbPost {
    return DbPost(
        id = id.value.toLong(),
        sortIndex = sortIndex.value.toLong(),
        name = name,
        creatorId = creator.id.value.toLong(),
        communityId = community.id.value.toLong(),
        isRemoved = isRemoved.toLong(),
        isLocked = isLocked.toLong(),
        publishedAt = publishedAt.format(formatter),
        isDeleted = isDeleted.toLong(),
        isNsfw = isNsfw.toLong(),
        apId = apId,
        isLocal = isLocal.toLong(),
        languageId = languageId.toLong(),
        isFeaturedCommunity = isFeaturedCommunity.toLong(),
        url = url?.value,
        body = body,
        updatedAt = updatedAt?.format(formatter),
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

fun PostSearchParameters.toApi(): GetPosts {
    return GetPosts(
        type = when (listingType) {
            DomainListingType.SUBSCRIBED -> {
                ApiListingType.subscribed
            }

            DomainListingType.ALL -> {
                ApiListingType.all
            }

            DomainListingType.LOCAL -> {
                ApiListingType.local
            }

            null -> null
        },
        sort = when (sortType) {
            SortType.HOT -> ApiSortType.hot
            SortType.NEW -> ApiSortType.new
            SortType.OLD -> ApiSortType.old
            SortType.ACTIVE -> ApiSortType.active
            SortType.TOP_DAY -> ApiSortType.topDay
            SortType.TOP_WEEK -> ApiSortType.topWeek
            SortType.TOP_MONTH -> ApiSortType.topMonth
            SortType.TOP_YEAR -> ApiSortType.topYear
            SortType.TOP_ALL -> ApiSortType.topAll
            SortType.MOST_COMMENTS -> ApiSortType.mostComments
            SortType.NEW_COMMENTS -> ApiSortType.newComments
            SortType.TOP_HOUR -> ApiSortType.topHour
            SortType.TOP_SIX_HOUR -> ApiSortType.topSixHour
            SortType.TOP_TWELVE_HOUR -> ApiSortType.topTwelveHour
            SortType.TOP_THREE_MONTHS -> ApiSortType.topThreeMonths
            SortType.TOP_SIX_MONTHS -> ApiSortType.topSixMonths
            SortType.TOP_NINE_MONTHS -> ApiSortType.topNineMonths
            null -> null
        },
    )
}
