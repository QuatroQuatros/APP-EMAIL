package com.example.challengelocaweb.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun convertTimestampToDate(timestamp: String): String
{
    val instant = Instant.parse(timestamp)

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("UTC"))
    return formatter.format(instant)

}

fun convertLongToTime(selectedDateMillis: Long): String {
    val date = Date(selectedDateMillis)

    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    return format.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidTime(time: String): Boolean {
    return try {
        LocalTime.parse(time)
        true
    } catch (e: Exception) {
        false
    }
}

