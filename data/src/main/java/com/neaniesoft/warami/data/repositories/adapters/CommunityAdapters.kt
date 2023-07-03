package com.neaniesoft.warami.data.repositories.adapters

import com.neaniesoft.warami.common.adapters.DomainCommunity
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.data.repositories.post.DbCommunity
import java.time.format.DateTimeFormatter

fun DomainCommunity.toDb(dateTimeFormatter: DateTimeFormatter): DbCommunity {
    return DbCommunity(
        id = id.value.toLong(),
        name = name,
        title = title,
        isRemoved = isRemoved.toLong(),
        published = publishedAt.format(dateTimeFormatter),
        isLocal = isLocal.toLong(),
        isDeleted = isDeleted.toLong(),
        isNsfw = isNsfw.toLong(),
        actorId = actorId.value,
        isHidden = isHidden.toLong(),
        isPostingRestrictedToMods = isPostingRestrictedToMods.toLong(),
        instanceId = instanceId.value.toLong(),
        description = description,
        updatedAt = updatedAt?.format(dateTimeFormatter),
        iconUrl = icon?.value,
        bannerUrl = banner?.value
    )
}
