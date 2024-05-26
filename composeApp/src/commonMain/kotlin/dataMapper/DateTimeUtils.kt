package dataMapper

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtils {
    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMillis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun fromEpochMillis(dateTime: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(dateTime).toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun formatNoteDate(dateTime: LocalDateTime): String {
       val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val year = dateTime.year
        val day = dateTime.dayOfMonth.let { if (it < 10) "0$it" else "$it" }
        val hour = dateTime.hour.let { if (it < 10) "0$it" else "$it" }
        val minute = dateTime.minute.let { if (it < 10) "0$it" else "$it" }
        return "$day/$month/$year $hour:$minute"
    }


}