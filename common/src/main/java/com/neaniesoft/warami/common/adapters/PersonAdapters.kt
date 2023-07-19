package com.neaniesoft.warami.common.adapters

import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.PersonId
import com.neaniesoft.warami.common.models.UriString
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun ApiPerson.toDomain(): DomainPerson {
    return DomainPerson(
        id = PersonId(id.intValueExact()),
        name = name,
        isBanned = banned,
        publishedAt = LocalDateTime.parse(published, DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC")),
        actorId = ActorId(actorId),
        isLocal = local,
        isDeleted = deleted,
        isAdmin = admin,
        isBotAccount = botAccount,
        instanceId = InstanceId(instanceId.intValueExact()),
        displayName = displayName,
        avatarUrl = avatar?.let { UriString(it) },
        updatedAt = updated?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("UTC")) },
        bio = bio,
        bannerUrl = banner?.let { UriString(it) },
        matrixUserId = matrixUserId,
        banExpiresAt = banExpires?.let {
            LocalDateTime.parse(
                it,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            ).atZone(ZoneId.of("UTC"))
        },
    )
}
