package com.neaniesoft.warami.data.repositories.adapters

import com.neaniesoft.warami.data.di.DatabaseScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import me.tatarka.inject.annotations.Inject
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@DatabaseScope
@Inject
class ZonedDateTimeFromLocalTimeAdapter : JsonAdapter<ZonedDateTime>() {
    override fun fromJson(reader: JsonReader): ZonedDateTime? {
        val string: String? = try {
            reader.nextString()
        } catch (e: JsonDataException) {
            reader.nextNull<String>()
        }
        return string?.let {
            try {
                LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            } catch (e: DateTimeParseException) {
                try {
                    LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                } catch (e: DateTimeParseException) {
                    try {
                        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"))
                    } catch (e: DateTimeParseException) {
                        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"))
                    }
                }
            }
        }?.atZone(ZoneId.of("UTC"))
    }

    override fun toJson(writer: JsonWriter, input: ZonedDateTime?) {
        val jsonDate = input?.toLocalDateTime()?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        if (jsonDate == null) {
            writer.nullValue()
        } else {
            writer.value(jsonDate)
        }
    }
}
