package com.neaniesoft.warami.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.neaniesoft.warami.api.apis.DefaultApi
import com.neaniesoft.warami.common.adapters.DomainCommunity
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.Post
import com.neaniesoft.warami.common.models.PostSearchParameters
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SelectBySearchParams
import com.neaniesoft.warami.data.repositories.adapters.toDb
import com.neaniesoft.warami.data.repositories.adapters.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.format.DateTimeFormatter

typealias DbCommunity = com.neaniesoft.warami.data.db.Community

class PostRepository(
    private val api: DefaultApi,
    private val postQueries: PostQueries,
    private val communityQueries: CommunityQueries,
    private val personQueries: PersonQueries,
    private val postAggregateQueries: PostAggregateQueries,
    private val postSearchParamsQueries: PostSearchParamsQueries,
    private val localDateTimeFormatter: DateTimeFormatter,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private fun SelectBySearchParams.toDomain(searchParameters: PostSearchParameters): Post =
        this.toDomain(localDateTimeFormatter, searchParameters)

    private fun DomainCommunity.toDb(): DbCommunity = this.toDb(localDateTimeFormatter)

    fun listPostsFlow(searchParameters: PostSearchParameters): Flow<List<Post>> {
        return postQueries.selectBySearchParams(searchParameters.id.toString()).asFlow()
            .mapToList(coroutineDispatcher)
            .map { select ->
                select.map { it.toDomain(searchParameters) }
            }
    }

    fun upsertPostList(postList: List<Post>) {
        postQueries.transaction {
            postList.forEach { post ->
                upsertPost(post)
            }
        }
    }

    private fun upsertPost(post: Post) {
        postQueries.transaction {
            with(post.community) {
                communityQueries.upsert(
                    name = name,
                    title = title,
                    isRemoved = isRemoved.toLong(),
                    published = publishedAt.format(localDateTimeFormatter),
                    isDeleted = isDeleted.toLong(),
                    isNsfw = isNsfw.toLong(),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isHidden = isHidden.toLong(),
                    isPostingRestrictedToMods = isPostingRestrictedToMods.toLong(),
                    instanceId = instanceId.value.toLong(),
                    description = description,
                    updatedAt = updatedAt?.format(localDateTimeFormatter),
                    iconUrl = icon?.value,
                    bannerUrl = banner?.value,
                    id = id.value.toLong()
                )
            }

            with(post.creator) {
                personQueries.upsert(
                    name = name,
                    isBanned = isBanned.toLong(),
                    publishedAt = publishedAt.format(localDateTimeFormatter),
                    actorId = actorId.value,
                    isLocal = isLocal.toLong(),
                    isDeleted = isDeleted.toLong(),
                    isAdmin = isAdmin.toLong(),
                    instanceId = instanceId.value.toLong(),
                    displayName = displayName,
                    avatarUrl = avatarUrl?.value,
                    updatedAt = updatedAt?.format(localDateTimeFormatter),
                    bio = bio,
                    bannerUrl = bannerUrl?.value,
                    matrixUserId = matrixUserId,
                    banExpires = banExpiresAt?.format(localDateTimeFormatter),
                    id = id.value.toLong(),
                    isBotAccount = isBotAccount.toLong()
                )
            }

            with(post.aggregates) {
                postAggregateQueries.upsert(
                    postId = postId.value.toLong(),
                    comments = commentCount.toLong(),
                    score = score.toLong(),
                    upVotes = votes.up.toLong(),
                    downVotes = votes.down.toLong(),
                    publishedAt = publishedAt.format(localDateTimeFormatter),
                    newestCommentTimeNecro = newestCommentTimeNecro.format(localDateTimeFormatter),
                    newestCommentTime = newestComment.format(localDateTimeFormatter),
                    isFeaturedCommunity = isFeaturedCommunity.toLong(),
                    isFeaturedLocal = isFeaturedLocal.toLong(),
                    hotRank = hotRank.toLong(),
                    hotRankActive = hotRankActive.toLong(),
                    id = id.toLong()
                )
            }

            with(post.searchParameters) {
                postSearchParamsQueries.upsert(
                    listingType = listingType?.value,
                    sortType = sortType?.value,
                    page = pageNumber?.toLong(),
                    postLimit = postLimit?.toLong(),
                    communityId = communityId?.value?.toLong(),
                    communityName = communityName,
                    isSavedOnly = isSavedOnly?.toLong(),
                    id = id.toString()
                )
            }
        }
    }
}
