package com.neaniesoft.warami.common.extensions

import android.content.res.Resources
import com.neaniesoft.warami.common.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun String.parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}

fun String.parseZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)
}

fun ZonedDateTime.formatPeriod(resources: Resources, comparison: ZonedDateTime): String {
    val minutes = ChronoUnit.MINUTES.between(this, comparison)
    if (minutes < 1) {
        return resources.getString(R.string.just_now)
    }

    val hours = ChronoUnit.HOURS.between(this, comparison)
    if (hours < 1) {
        return resources.getString(R.string.minutes_ago, minutes)
    }

    val days = ChronoUnit.DAYS.between(this, comparison)
    if (days < 1) {
        return resources.getString(R.string.hours_ago, hours)
    }

    return resources.getString(R.string.days_ago, days)

}
