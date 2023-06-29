package com.neaniesoft.warami.common.adapters

import com.neaniesoft.warami.api.models.Community
import com.neaniesoft.warami.common.models.ActorId
import com.neaniesoft.warami.common.models.CommunityId
import com.neaniesoft.warami.common.models.InstanceId
import com.neaniesoft.warami.common.models.UriString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

typealias ApiCommunity = Community
typealias DomainCommunity = com.neaniesoft.warami.common.models.Community

fun ApiCommunity.toDomain(): DomainCommunity = DomainCommunity(
    id = CommunityId(id.intValueExact()),
    name = name,
    title = title,
    isRemoved = removed,
    publishedAt = LocalDateTime.parse(published, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    isDeleted = deleted,
    isNsfw = nsfw,
    actorId = ActorId(actorId),
    isLocal = local,
    isHidden = hidden,
    isPostingRestrictedToMods = postingRestrictedToMods,
    instanceId = InstanceId(instanceId.intValueExact()),
    description = description,
    updatedAt = updated?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) },
    icon = icon?.let { UriString(it) },
    banner = banner?.let { UriString(it) },
)
