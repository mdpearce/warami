package com.neaniesoft.warami.common.extensions

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}

fun String.parseZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)
}
