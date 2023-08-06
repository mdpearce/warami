package com.neaniesoft.warami.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toBoolean
import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.Community
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.di.IODispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor(
    private val communityQueries: CommunityQueries,
    private val ioDispatcher: IODispatcher,
) {
    fun getCommunity(communityId: CommunityId): Flow<Community> {
        return communityQueries.transactionWithResult {
            communityQueries.selectCommunity(communityId.value.toLong()).asFlow()
        }.mapToOne(ioDispatcher).map { db ->
            Community(
                id = CommunityId(db.id.toInt()),
                name = db.name,
                title = db.title,
                isRemoved = db.isRemoved.toBoolean(),
                publishedAt = db.published.parseZonedDateTime(),
                isDeleted = db.isDeleted.toBoolean(),
                isNsfw = db.isNsfw.toBoolean(),
                actorId = ActorId(db.actorId),
                isLocal = db.isLocal.toBoolean(),
                isHidden = db.isHidden.toBoolean(),
                isPostingRestrictedToMods = db.isPostingRestrictedToMods.toBoolean(),
                instanceId = InstanceId(db.instanceId.toInt()),
                description = db.description,
                updatedAt = db.updatedAt?.parseZonedDateTime(),
                icon = db.iconUrl?.let { UriString(it) },
                banner = db.bannerUrl?.let { UriString(it) },
            )
        }
    }
}
