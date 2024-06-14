package com.example.challengelocaweb.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun converteTimestampToDate(timestamp: String): String
{
    val instant = Instant.parse(timestamp)

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("UTC"))
    return formatter.format(instant)

}