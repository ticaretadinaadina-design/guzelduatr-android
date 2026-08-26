package com.guzelduatr.app.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object TimeUtils {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun parseTimeToNextDelayMillis(timeStr: String): Long {
        val now = ZonedDateTime.now()
        val localTime = LocalTime.parse(timeStr, timeFormatter)
        var dateTime = now.withHour(localTime.hour).withMinute(localTime.minute).withSecond(0).withNano(0)
        if (dateTime.isBefore(now) || dateTime.isEqual(now)) {
            dateTime = dateTime.plusDays(1)
        }
        return ChronoUnit.MILLIS.between(now, dateTime)
    }
}
