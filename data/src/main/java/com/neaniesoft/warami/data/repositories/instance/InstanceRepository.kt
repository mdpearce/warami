package com.neaniesoft.warami.data.repositories.instance

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.neaniesoft.warami.common.extensions.parseZonedDateTime
import com.neaniesoft.warami.common.extensions.toBoolean
import com.neaniesoft.warami.common.extensions.toLong
import com.neaniesoft.warami.common.models.UriString
import com.neaniesoft.warami.data.db.InstanceQueries
import com.neaniesoft.warami.data.di.DatabaseScope
import com.neaniesoft.warami.data.di.IODispatcher
import com.neaniesoft.warami.data.repositories.RemoteConfigRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanceRepository @Inject constructor(
    private val instanceQueries: InstanceQueries,
    private val client: OkHttpClient,
    private val remoteConfigRepository: RemoteConfigRepository,
    private val dispatcher: IODispatcher,
    private val moshi: Moshi,
) {
    companion object {
        private const val REMOTE_CONFIG_KEY = "lemmyverse_instances_download_url"
    }

    val instances: Flow<List<DomainInstance>>
        get() = instanceQueries.selectAll().asFlow().mapToList(dispatcher)
            .map { instances ->
                instances.map { instance ->
                    instance.toDomain()
                }
            }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun fetchAndPopulateInstanceMetadata() {
        val url = remoteConfigRepository.fetchAndActivate().run {
            remoteConfigRepository.getString(REMOTE_CONFIG_KEY)
        }

        val request = Request.Builder().apply {
            get()
            url(url)
        }.build()

        val freshInstances = withContext(dispatcher) {
            val response = client.newCall(request).execute()

            val responseStream = response.body?.source()

            if (responseStream != null) {
                moshi.adapter<List<Instance>>()
                    .fromJson(responseStream)
            } else {
                null
            }
        }

        if (freshInstances != null) {
            instanceQueries.transaction {
                instanceQueries.deleteAll()
                freshInstances.forEach { apiInstance ->
                    instanceQueries.insert(
                        apiInstance.toDbInstance(),
                    )
                }
            }
        }
    }

    private fun ApiInstance.toDbInstance(): DbInstance {
        return DbInstance(
            id = null,
            baseUrl = baseUrl,
            url = url,
            name = name,
            description = description,
            downVotes = downVotes?.toLong(),
            hasNsfw = hasNsfw?.toLong(),
            createAdmin = createAdmin?.toLong(),
            isPrivate = isPrivate?.toLong(),
            isFed = isFed?.toLong(),
            date = date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            version = version,
            isOpen = isOpen.toLong(),
            iconUrl = iconUrl,
            bannerUrl = bannerUrl,
            languages = languages.joinToString(","),
            time = time,
            score = score.toLong(),
            isSuspicious = isSuspicious.toLong(),
            usageTotalUsers = usage.users.total.toLong(),
            usageActiveHalfYear = usage.users.activeHalfYear.toLong(),
            usageActiveMonth = usage.users.activeMonth.toLong(),
            usageLocalPosts = usage.localPosts.toLong(),
            usageLocalComments = usage.localComments.toLong(),
            countUsers = counts.users.toLong(),
            countPosts = counts.posts.toLong(),
            countComments = counts.comments.toLong(),
            countCommunities = counts.communities.toLong(),
            countUsersActiveDay = counts.usersActiveDay.toLong(),
            countUsersActiveWeek = counts.usersActiveWeek.toLong(),
            countUsersActiveMonth = counts.usersActiveMonth.toLong(),
            countUsersActiveHalfYear = counts.usersActiveHalfYear.toLong(),
            uptimeDomain = uptime?.domain,
            uptimeLatency = uptime?.latency?.toLong(),
            uptimeCountryName = uptime?.countryName,
            uptimeAllTime = uptime?.uptimeAllTime,
            uptimeDateCreated = uptime?.dateCreated?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            uptimeDateUpdated = uptime?.dateUpdated?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            uptimeDateLastStats = uptime?.dateLastStats?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            uptimeScore = uptime?.score?.toLong(),
            uptimeStatus = uptime?.status?.toLong(),
            metricsUsersTotal = metrics.usersTotal.toLong(),
            metricsUsersMonth = metrics.usersMonth.toLong(),
            metricsUsersWeek = metrics.usersWeek.toLong(),
            metricsTotalActivity = metrics.totalActivity.toLong(),
            metricsLocalPosts = metrics.localPosts.toLong(),
            metricsLocalComments = metrics.localComments.toLong(),
            metricsAverageUsers = metrics.averageUsers,
            metricsBiggestJump = metrics.biggestJump.toLong(),
            metricsAveragePerMinute = metrics.averagePerMinute,
            metricsUserActivityScore = metrics.userActivityScore,
            metricsActivityUserScore = metrics.activityUserScore,
            metricsUserActiveMonthScore = metrics.userActiveMonthScore,
            blocksIncoming = blocks.incoming.toLong(),
            blocksOutgoing = blocks.outgoing.toLong(),
        )
    }

    private fun DbInstance.toDomain(): DomainInstance {
        return DomainInstance(
            baseUrl = UriString(baseUrl),
            url = UriString(url),
            name = name,
            description = description,
            hasDownVotes = downVotes?.toBoolean(),
            hasNsfw = hasNsfw?.toBoolean(),
            createAdmin = createAdmin?.toBoolean(),
            isPrivate = isPrivate?.toBoolean(),
            isFed = isFed?.toBoolean(),
            date = date.parseZonedDateTime(),
            version = version,
            isOpen = isOpen.toBoolean(),
            iconUrl = iconUrl?.let { UriString(it) },
            bannerUrl = bannerUrl?.let { UriString(it) },
            languages = languages.split(","),
            time = Date(time),
            score = score.toInt(),
            isSuspicious = isSuspicious.toBoolean(),
            usage = com.neaniesoft.warami.common.models.Instance.Usage(
                users = com.neaniesoft.warami.common.models.Instance.Usage.Users(
                    total = usageTotalUsers.toInt(),
                    activeHalfYear = usageActiveHalfYear.toInt(),
                    activeMonth = usageActiveMonth.toInt(),
                ),
                localPosts = usageLocalPosts.toInt(),
                localComments = usageLocalComments.toInt(),
            ),
            counts = com.neaniesoft.warami.common.models.Instance.Counts(
                users = countUsers.toInt(),
                posts = countPosts.toInt(),
                comments = countComments.toInt(),
                communities = countCommunities.toInt(),
                usersActiveDay = countUsersActiveDay.toInt(),
                usersActiveWeek = countUsersActiveWeek.toInt(),
                usersActiveMonth = countUsersActiveMonth.toInt(),
                usersActiveHalfYear = countUsersActiveHalfYear.toInt(),
            ),
            uptime = if (uptimeDomain != null && uptimeLatency != null && uptimeCountryName != null && uptimeAllTime != null && uptimeDateCreated != null && uptimeDateLastStats != null && uptimeScore != null && uptimeStatus != null) {
                com.neaniesoft.warami.common.models.Instance.Uptime(
                    domain = uptimeDomain,
                    latency = uptimeLatency.toInt(),
                    countryName = uptimeCountryName,
                    uptimeAllTime = uptimeAllTime,
                    dateCreated = uptimeDateCreated.parseZonedDateTime(),
                    dateLastStats = uptimeDateLastStats.parseZonedDateTime(),
                    score = uptimeScore.toInt(),
                    status = uptimeStatus.toInt(),
                )
            } else {
                null
            },
            metrics = com.neaniesoft.warami.common.models.Instance.Metrics(
                usersTotal = metricsUsersTotal.toInt(),
                usersMonth = metricsUsersMonth.toInt(),
                usersWeek = metricsUsersWeek.toInt(),
                totalActivity = metricsTotalActivity.toInt(),
                localPosts = metricsLocalPosts.toInt(),
                localComments = metricsLocalComments.toInt(),
                averageUsers = metricsAverageUsers,
                biggestJump = metricsBiggestJump.toInt(),
                averagePerMinute = metricsAveragePerMinute,
                userActivityScore = metricsUserActivityScore,
                activityUserScore = metricsActivityUserScore,
                userActiveMonthScore = metricsUserActiveMonthScore,
            ),
            blocks = com.neaniesoft.warami.common.models.Instance.Blocks(
                incoming = blocksIncoming.toInt(),
                outgoing = blocksOutgoing.toInt(),
            ),
        )
    }
}

internal typealias ApiInstance = Instance
internal typealias DbInstance = com.neaniesoft.warami.data.db.Instance
internal typealias DomainInstance = com.neaniesoft.warami.common.models.Instance
