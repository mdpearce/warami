package com.neaniesoft.warami.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.extensions.toBoolean
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
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.SelectBySearchParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostRepository(
    private val api: DefaultApi,
    private val postQueries: PostQueries,
    private val localDateTimeFormatter: DateTimeFormatter,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private fun SelectBySearchParams.toDomain(searchParameters: PostSearchParameters): Post =
        this.toDomain(localDateTimeFormatter, searchParameters)

    fun listPosts(searchParameters: PostSearchParameters): List<Post> {
        val selectResult =
            postQueries.selectBySearchParams(searchParameters.id.toLong()).executeAsList()

        return selectResult.map { select ->
            select.toDomain(searchParameters)
        }
    }

    fun listPostsFlow(searchParameters: PostSearchParameters): Flow<List<Post>> {
        return postQueries.selectBySearchParams(searchParameters.id.toLong()).asFlow()
            .mapToList(coroutineDispatcher)
            .map { select ->
                select.map { it.toDomain(searchParameters) }
            }
    }

}

fun SelectBySearchParams.toDomain(
    dateTimeFormatter: DateTimeFormatter,
    searchParameters: PostSearchParameters
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
        banner = communityBannerUrl?.let { UriString(it) }
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
        banExpiresAt = creatorBanExpires?.parseLocalDateTime()
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
        hotRankActive = countsHotRankActive.toInt()
    )

    return Post(
        id = PostId(id.toInt()),
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
        searchParameters = searchParameters
    )
}

fun String.parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}
