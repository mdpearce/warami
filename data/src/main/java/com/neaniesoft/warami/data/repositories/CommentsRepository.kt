package com.neaniesoft.warami.data.repositories

import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.extensions.parseLocalDateTime
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toBoolean
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.ApId
import com.neaniesoft.warami.common.models.ChildCount
import com.neaniesoft.warami.common.models.Comment
import com.neaniesoft.warami.common.models.CommentAggregates
import com.neaniesoft.warami.common.models.CommentId
import com.neaniesoft.warami.common.models.CommentPath
import com.neaniesoft.warami.common.models.CommentSearchParameters
import com.neaniesoft.warami.common.models.CommentSortType
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.HotRank
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.LanguageId
import com.neaniesoft.warami.common.models.ListingType
import com.neaniesoft.warami.common.models.PageNumber
import com.neaniesoft.warami.common.models.Person
import com.neaniesoft.warami.common.models.PersonId
import com.neaniesoft.warami.common.models.PostId
import com.neaniesoft.warami.common.models.Score
import com.neaniesoft.warami.common.models.SubscribedType
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.common.models.Vote
import com.neaniesoft.warami.common.models.Votes
import com.neaniesoft.warami.common.models.compareTo
import com.neaniesoft.warami.data.db.CommentQueries
import com.neaniesoft.warami.data.db.PersonQueries
import retrofit2.HttpException
import java.io.IOException
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentsRepository @Inject constructor(
    private val commentQueries: CommentQueries,
    private val apiRepository: ApiRepository,
    private val personQueries: PersonQueries,
) {
    private val api: DefaultApi
        get() {
            return apiRepository.api.value
        }

    companion object {
        private const val LIMIT = 200
    }

    suspend fun getComments(commentSearchParameters: CommentSearchParameters, pageNumber: PageNumber): List<Comment> {
        val apiCommentsResponse = with(commentSearchParameters) {
            try {
                api.getComments(
                    type = when (commentSearchParameters.listingType) {
                        ListingType.SUBSCRIBED -> ApiListingType.subscribed
                        ListingType.ALL -> ApiListingType.all
                        ListingType.LOCAL -> ApiListingType.local
                        null -> null
                    },
                    sort = when (commentSearchParameters.commentSortType) {
                        CommentSortType.HOT -> com.neaniesoft.warami.api.models.CommentSortType.hot
                        CommentSortType.TOP -> com.neaniesoft.warami.api.models.CommentSortType.top
                        CommentSortType.NEW -> com.neaniesoft.warami.api.models.CommentSortType.new
                        CommentSortType.OLD -> com.neaniesoft.warami.api.models.CommentSortType.old
                        null -> null
                    },
                    maxDepth = maxDepth?.toBigDecimal(),
                    page = pageNumber.value.toBigDecimal(),
                    limit = LIMIT.toBigDecimal(),
                    communityId = communityId?.value?.toBigDecimal(),
                    communityName = communityName,
                    postId = postId?.value?.toBigDecimal(),
                    parentId = parentId?.value?.toBigDecimal(),
                    savedOnly = isSavedOnly,
                    auth = null, // TODO do... uh, auth.
                )
            } catch (e: IOException) {
                throw CommentsRepositoryException("IO Error from API", e)
            } catch (e: HttpException) {
                throw CommentsRepositoryException("Http Error from API", e)
            }
        }

        val apiComments = if (!apiCommentsResponse.isSuccessful) {
            throw CommentsRepositoryException(
                "Unsuccessful HTTP response: ${apiCommentsResponse.code()}\n${
                    apiCommentsResponse.errorBody()?.string()
                }",
            )
        } else {
            val body = apiCommentsResponse.body()
            body?.comments ?: throw CommentsRepositoryException("Null response body")
        }

        val mappedComments = commentQueries.transactionWithResult {
            if (pageNumber <= 1) {
                commentQueries.deleteAllForSearchParams(commentSearchParameters.id)
            }

            apiComments.forEach { commentView ->
                with(commentView) {
                    personQueries.upsert(
                        name = creator.name,
                        isBanned = creator.banned.toLong(),
                        publishedAt = creator.published.parseLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC"))
                            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                        actorId = creator.actorId,
                        isLocal = creator.local.toLong(),
                        isDeleted = creator.deleted.toLong(),
                        isAdmin = creator.admin.toLong(),
                        instanceId = creator.instanceId.toLong(),
                        displayName = creator.displayName,
                        avatarUrl = creator.avatar,
                        updatedAt = creator.updated?.let {
                            it.parseLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC"))
                                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        },
                        bio = creator.bio,
                        bannerUrl = creator.banner,
                        matrixUserId = creator.matrixUserId,
                        banExpires = creator.banExpires?.let {
                            it.parseLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC"))
                                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                        },
                        id = creator.id.toLong(),
                        isBotAccount = creator.botAccount.toLong(),
                    )


                    commentQueries.insert(
                        searchParams = commentSearchParameters.id,
                        commentId = comment.id.toLong(),
                        creatorId = creator.id.toLong(),
                        postId = post.id.toLong(),
                        communityId = community.id.toLong(),
                        content = comment.content,
                        isRemoved = comment.removed.toLong(),
                        publishedAt = comment.published.parseLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC"))
                            .format(
                                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                            ),
                        isDeleted = comment.deleted.toLong(),
                        apId = comment.apId,
                        isLocal = comment.local.toLong(),
                        path = comment.path,
                        isDistinguished = comment.distinguished.toLong(),
                        languageId = comment.languageId.toLong(),
                        updatedAt = comment.updated?.let {
                            it.parseLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC")).format(
                                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                            )
                        },
                        aggregatesScore = counts.score.toLong(),
                        aggregatesUpVotes = counts.upvotes.toLong(),
                        aggregatesDownVotes = counts.downvotes.toLong(),
                        aggregatesChildCount = counts.childCount.toLong(),
                        aggregatesHotRank = counts.hotRank.toLong(),
                        isCreatorBannedFromCommunity = creatorBannedFromCommunity.toLong(),
                        subscribed = subscribed.value,
                        isSaved = saved.toLong(),
                        isCreatorBlocked = creatorBlocked.toLong(),
                        myVote = myVote?.toLong(),
                    )
                }
            }

            commentQueries.select(commentSearchParameters.id).executeAsList().map { dbComment ->
                Comment(
                    commentId = CommentId(dbComment.commentId.toInt()),
                    creator = Person(
                        id = PersonId(dbComment.creatorId.toInt()),
                        name = dbComment.creatorName,
                        isBanned = dbComment.creatorIsBanned.toBoolean(),
                        publishedAt = dbComment.creatorPublishedAt.parseZonedDateTime(),
                        actorId = ActorId(dbComment.creatorActorId),
                        isLocal = dbComment.creatorIsLocal.toBoolean(),
                        isDeleted = dbComment.creatorIsDeleted.toBoolean(),
                        isAdmin = dbComment.creatorIsAdmin.toBoolean(),
                        isBotAccount = dbComment.creatorIsBotAccount.toBoolean(),
                        instanceId = InstanceId(dbComment.creatorInstanceId.toInt()),
                        displayName = dbComment.creatorDisplayName,
                        avatarUrl = dbComment.creatorAvatarUrl?.let { UriString(it) },
                        updatedAt = dbComment.creatorUpdatedAt?.parseZonedDateTime(),
                        bio = dbComment.creatorBio,
                        bannerUrl = dbComment.creatorBannerUrl?.let { UriString(it) },
                        matrixUserId = dbComment.creatorMatrixUserId,
                        banExpiresAt = dbComment.creatorBanExpires?.let { it.parseZonedDateTime() },
                    ),
                    postId = PostId(dbComment.postId.toInt()),
                    communityId = CommunityId(dbComment.communityId.toInt()),
                    content = dbComment.content,
                    isRemoved = dbComment.isRemoved.toBoolean(),
                    publishedAt = dbComment.publishedAt.parseZonedDateTime(),
                    isDeleted = dbComment.isDeleted.toBoolean(),
                    apId = ApId(dbComment.apId),
                    isLocal = dbComment.isLocal.toBoolean(),
                    path = CommentPath(dbComment.path),
                    isDistinguished = dbComment.isDistinguished.toBoolean(),
                    languageId = LanguageId(dbComment.languageId.toInt()),
                    updatedAt = dbComment.updatedAt?.let { it.parseZonedDateTime() },
                    counts = CommentAggregates(
                        score = Score(dbComment.aggregatesScore.toInt()),
                        votes = Votes(up = dbComment.aggregatesUpVotes.toInt(), down = dbComment.aggregatesDownVotes.toInt()),
                        childCount = ChildCount(dbComment.aggregatesChildCount.toInt()),
                        hotRank = HotRank(dbComment.aggregatesHotRank.toInt()),
                    ),
                    isCreatorBannedFromCommunity = dbComment.isCreatorBannedFromCommunity.toBoolean(),
                    subscribedType = SubscribedType.parse(dbComment.subscribed),
                    isSaved = dbComment.isSaved.toBoolean(),
                    isCreatorBlocked = dbComment.isCreatorBlocked.toBoolean(),
                    myVote = dbComment.myVote?.let { Vote(it.toInt()) },
                )
            }

        }

        return mappedComments
    }
}

data class CommentsRepositoryException(override val message: String = "", override val cause: Throwable? = null) : Exception(message, cause)
