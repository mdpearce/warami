package com.neaniesoft.warami.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
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
import com.neaniesoft.warami.data.db.SelectPostsOffset
import com.neaniesoft.warami.data.repositories.post.PostRepository
import com.neaniesoft.warami.data.repositories.post.PostTransactor
import com.neaniesoft.warami.data.repositories.post.createPager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPagingDataForPostsUseCase
@Inject
constructor(
    private val postRepository: PostRepository,
    private val postTransactor: PostTransactor,
) {
    operator fun invoke(searchParams: PostSearchParameters): Flow<PagingData<Post>> {
        return createPager(searchParams, postRepository, postTransactor).flow
            .map { pagingData ->
                pagingData.map { dbPost ->
                    dbPost.toDomain(searchParams)
                }
            }
    }
}

private fun SelectPostsOffset.toDomain(searchParams: PostSearchParameters): Post = Post(
    postId = PostId(postId.toInt()),
    insertedAt = insertedAt.parseZonedDateTime(),
    name = name,
    creator = Person(
        id = PersonId(creatorId.toInt()),
        name = creatorName,
        isBanned = creatorIsBanned.toBoolean(),
        publishedAt = publishedAt.parseZonedDateTime(),
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
    ),
    community = Community(
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
    ),
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
    aggregates = PostAggregates(
        id = 0,
        postId = PostId(postId.toInt()),
        commentCount = countsComments.toInt(),
        score = countsScore.toInt(),
        votes = Votes(up = countsUpVotes.toInt(), down = countsDownVotes.toInt()),
        publishedAt = countsPublishedAt.parseZonedDateTime(),
        newestCommentTimeNecro = countsNewestCommentTimeNecro.parseZonedDateTime(),
        newestComment = countsNewestCommentTime.parseZonedDateTime(),
        isFeaturedCommunity = countsIsFeaturedCommunity.toBoolean(),
        hotRank = countsHotRank.toInt(),
        hotRankActive = countsHotRankActive.toInt(),
        isFeaturedLocal = countsIsFeaturedLocal.toBoolean(),
    ),
    subscribedTyped = SubscribedType.parse(subscribedType),
    isSaved = isSaved.toBoolean(),
    isRead = isRead.toBoolean(),
    isCreatorBlocked = isCreatorBlocked.toBoolean(),
    myVote = myVote?.toInt(),
    searchParameters = searchParams,
)
