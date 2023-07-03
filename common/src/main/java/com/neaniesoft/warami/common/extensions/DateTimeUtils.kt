package com.neaniesoft.warami.common.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.parseLocalDateTime(formatter: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}
