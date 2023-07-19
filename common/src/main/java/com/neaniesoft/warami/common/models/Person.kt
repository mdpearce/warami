package com.neaniesoft.warami.common.models

import java.time.ZonedDateTime

data class Person(
    val id: PersonId,
    val name: String,
    val isBanned: Boolean,
    val publishedAt: ZonedDateTime,
    val actorId: ActorId,
    val isLocal: Boolean,
    val isDeleted: Boolean,
    val isAdmin: Boolean,
    val isBotAccount: Boolean,
    val instanceId: InstanceId,
    val displayName: String?,
    val avatarUrl: UriString?,
    val updatedAt: ZonedDateTime?,
    val bio: String?,
    val bannerUrl: UriString?,
    val matrixUserId: String?,
    val banExpiresAt: ZonedDateTime?,
)
