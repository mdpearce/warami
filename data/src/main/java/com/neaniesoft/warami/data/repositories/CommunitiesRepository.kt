package com.neaniesoft.warami.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toBoolean
import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.Community
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.SubscribedCommunity
import com.neaniesoft.warami.common.models.SubscribedType
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.db.SelectSubscribedCommunities
import com.neaniesoft.warami.data.db.SubscribedCommunityQueries
import com.neaniesoft.warami.data.di.IODispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunitiesRepository @Inject constructor(
    private val apiRepository: ApiRepository,
    private val authRepository: AuthRepository,
    private val subscribedCommunityQueries: SubscribedCommunityQueries,
    private val dispatcher: IODispatcher,
) {

    val subscribedCommunities: Flow<List<SubscribedCommunity>> =
        subscribedCommunityQueries.selectSubscribedCommunities(SubscribedType.SUBSCRIBED.value, SubscribedType.PENDING.value).asFlow()
            .mapToList(dispatcher)
            .map { subscribedCommunities ->
                subscribedCommunities.map { dbCommunity ->
                    dbCommunity.toDomain()
                }
            }


    suspend fun updateSubscribedCommunities() {
//        try {
//            val communitiesApiResponse = apiRepository.api.value.listCommunities(
//                type = ListingType.subscribed,
//                auth = authRepository.jwt.value
//            )
//            val body = communitiesApiResponse.body()
//            val errorBody = communitiesApiResponse.errorBody()
//            if (communitiesApiResponse.isSuccessful && body != null) {
//                val apiCommunities = body.communities
//
//            }
//
//        } catch (e: HttpException) {
//
//        } catch (e: IOException) {
//
//        }
    }

}

fun SelectSubscribedCommunities.toDomain(): SubscribedCommunity =
    SubscribedCommunity(
        community = Community(
            id = CommunityId(id.toInt()),
            name = name,
            title = title,
            isRemoved = isRemoved.toBoolean(),
            publishedAt = published.parseZonedDateTime(),
            isDeleted = isDeleted.toBoolean(),
            isNsfw = isNsfw.toBoolean(),
            actorId = ActorId(actorId),
            isLocal = isLocal.toBoolean(),
            isHidden = isHidden.toBoolean(),
            isPostingRestrictedToMods = isPostingRestrictedToMods.toBoolean(),
            instanceId = InstanceId(instanceId.toInt()),
            description = description,
            updatedAt = updatedAt?.parseZonedDateTime(),
            icon = iconUrl?.let { UriString(it) },
            banner = bannerUrl?.let { UriString(it) },
        ),
        subscribedType = subscriptionStatus?.let { SubscribedType.parse(it) } ?: SubscribedType.NOT_SUBSCRIBED,
    )
