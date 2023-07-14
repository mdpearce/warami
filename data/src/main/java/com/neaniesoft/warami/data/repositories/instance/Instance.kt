package com.neaniesoft.warami.data.repositories.instance

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
internal data class Instance(
    @Json(name = "baseurl")
    val baseUrl: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "desc")
    val description: String,
    @Json(name = "downvotes")
    val downVotes: Boolean,
    @Json(name = "nsfw")
    val hasNsfw: Boolean,
    @Json(name = "create_admin")
    val createAdmin: Boolean,
    @Json(name = "private")
    val isPrivate: Boolean,
    @Json(name = "fed")
    val isFed: Boolean,
    @Json(name = "date")
    val date: ZonedDateTime,
    @Json(name = "version")
    val version: String,
    @Json(name = "open")
    val isOpen: Boolean,
    @Json(name = "usage")
    val usage: InstanceUsage,
    @Json(name = "counts")
    val counts: InstanceCounts,
    @Json(name = "icon")
    val iconUrl: String?,
    @Json(name = "banner")
    val bannerUrl: String?,
    @Json(name = "langs")
    val languages: List<String>,
    @Json(name = "time")
    val time: Long,
    @Json(name = "score")
    val score: Int,
    @Json(name = "uptime")
    val uptime: InstanceUptime,
    @Json(name = "isSuspicious")
    val isSuspicious: Boolean,
    @Json(name = "metrics")
    val metrics: InstanceMetrics,
    @Json(name = "blocks")
    val blocks: InstanceBlocks,
)

@JsonClass(generateAdapter = true)
internal data class InstanceUsage(
    @Json(name = "user")
    val users: InstanceUsers,
    @Json(name = "localPosts")
    val localPosts: Int,
    @Json(name = "localComments")
    val localComments: Int,
)

@JsonClass(generateAdapter = true)
internal data class InstanceUsers(
    @Json(name = "total")
    val total: Int,
    @Json(name = "activeHalfyear")
    val activeHalfYear: Int,
    @Json(name = "activeMonth")
    val activeMonth: Int,
)

@JsonClass(generateAdapter = true)
internal data class InstanceCounts(
    @Json(name = "id")
    val id: Int,
    @Json(name = "site_id")
    val siteId: Int,
    @Json(name = "users")
    val users: Int,
    @Json(name = "posts")
    val posts: Int,
    @Json(name = "comments")
    val comments: Int,
    @Json(name = "communities")
    val communities: Int,
    @Json(name = "users_active_day")
    val usersActiveDay: Int,
    @Json(name = "users_active_week")
    val usersActiveWeek: Int,
    @Json(name = "users_active_month")
    val usersActiveMonth: Int,
    @Json(name = "users_active_half_year")
    val userActiveHalfYear: Int,
)

@JsonClass(generateAdapter = true)
internal data class InstanceUptime(
    @Json(name = "domain")
    val domain: String,
    @Json(name = "latency")
    val latency: Int,
    @Json(name = "countryname")
    val countryName: String,
    @Json(name = "uptime_alltime")
    val uptimeAllTime: Double,
    @Json(name = "date_created")
    val dateCreated: ZonedDateTime,
    @Json(name = "date_updated")
    val dateUpdated: ZonedDateTime,
    @Json(name = "date_laststats")
    val dateLastStats: ZonedDateTime,
    @Json(name = "score")
    val score: Int,
    @Json(name = "status")
    val status: Int,
)

@JsonClass(generateAdapter = true)
internal data class InstanceMetrics(
    @Json(name = "usersTotal")
    val usersTotal: Int,
    @Json(name = "usersMonth")
    val usersMonth: Int,
    @Json(name = "usersWeek")
    val usersWeek: Int,
    @Json(name = "totalActivity")
    val totalActivity: Int,
    @Json(name = "localPosts")
    val localPosts: Int,
    @Json(name = "localComments")
    val localComments: Int,
    @Json(name = "averageUsers")
    val averageUsers: Int,
    @Json(name = "biggestJump")
    val biggestJump: Int,
    @Json(name = "averagePerMinute")
    val averagePerMinute: Int,
    @Json(name = "userActivityScore")
    val userActivityScore: Double,
    @Json(name = "activityUserScore")
    val activityUserScore: Double,
    @Json(name = "userActiveMonthScore")
    val userActiveMonthScore: Int,
)

@JsonClass(generateAdapter = true)
internal data class InstanceBlocks(
    @Json(name = "incoming")
    val incoming: Int,
    @Json(name = "outgoing")
    val outgoing: Int,
)
