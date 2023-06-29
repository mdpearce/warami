package com.neaniesoft.warami.common.models

import java.time.LocalDateTime
import java.time.ZonedDateTime

data class Community(
    val id: CommunityId,
    val name: String,
    val title: String,
    val isRemoved: Boolean,
    val publishedAt: LocalDateTime,
    val isDeleted: Boolean,
    val isNsfw: Boolean,
    val actorId: ActorId,
    val isLocal: Boolean,
    val isHidden: Boolean,
    val isPostingRestrictedToMods: Boolean,
    val instanceId: InstanceId,
    val description: String?,
    val updatedAt: LocalDateTime?,
    val icon: UriString?,
    val banner: UriString?,
)
