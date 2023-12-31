package com.neaniesoft.warami.common.models

import java.time.ZonedDateTime

data class Community(
    val id: CommunityId,
    val name: String,
    val title: String,
    val isRemoved: Boolean,
    val publishedAt: ZonedDateTime,
    val isDeleted: Boolean,
    val isNsfw: Boolean,
    val actorId: ActorId,
    val isLocal: Boolean,
    val isHidden: Boolean,
    val isPostingRestrictedToMods: Boolean,
    val instanceId: InstanceId,
    val description: String?,
    val updatedAt: ZonedDateTime?,
    val icon: UriString?,
    val banner: UriString?,
)
