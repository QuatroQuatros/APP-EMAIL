package com.example.challengelocaweb.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalTime(time: LocalTime): String {
        return time.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime {
        return LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME)
    }
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

}