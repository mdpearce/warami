package com.neaniesoft.warami.common.models

import java.time.ZonedDateTime
import java.util.Date

data class Instance(
    val baseUrl: UriString,
    val url: UriString,
    val name: String,
    val description: String,
    val hasDownVotes: Boolean?,
    val hasNsfw: Boolean?,
    val createAdmin: Boolean?,
    val isPrivate: Boolean?,
    val isFed: Boolean?,
    val date: ZonedDateTime,
    val version: String,
    val isOpen: Boolean,
    val iconUrl: UriString?,
    val bannerUrl: UriString?,
    val languages: List<String>,
    val time: Date,
    val score: Int,
    val isSuspicious: Boolean,
    val usage: Usage,
    val counts: Counts,
    val uptime: Uptime?,
    val metrics: Metrics,
    val blocks: Blocks,
) {
    data class Usage(
        val users: Users,
        val localPosts: Int,
        val localComments: Int,
    ) {

        data class Users(
            val total: Int,
            val activeHalfYear: Int,
            val activeMonth: Int,
        )
    }

    data class Counts(
        val users: Int,
        val posts: Int,
        val comments: Int,
        val communities: Int,
        val usersActiveDay: Int,
        val usersActiveWeek: Int,
        val usersActiveMonth: Int,
        val usersActiveHalfYear: Int,
    )

    data class Uptime(
        val domain: String,
        val latency: Int,
        val countryName: String?,
        val uptimeAllTime: Double,
        val dateCreated: ZonedDateTime,
        val dateLastStats: ZonedDateTime,
        val score: Int,
        val status: Int,
    )

    data class Metrics(
        val usersTotal: Int,
        val usersMonth: Int,
        val usersWeek: Int,
        val totalActivity: Int,
        val localPosts: Int,
        val localComments: Int,
        val averageUsers: Double,
        val biggestJump: Int,
        val averagePerMinute: Double,
        val userActivityScore: Double,
        val activityUserScore: Double,
        val userActiveMonthScore: Double,
    )

    data class Blocks(
        val incoming: Int,
        val outgoing: Int,
    )
}
