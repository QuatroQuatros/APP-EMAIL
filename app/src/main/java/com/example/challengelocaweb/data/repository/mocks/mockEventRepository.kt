package com.example.challengelocaweb.data.repository.mocks

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.repository.EventRepository
import com.example.challengelocaweb.presentation.event.components.EventTypeEnum
import java.time.LocalTime

fun mockEventRepository(): EventRepository {
    return object : EventRepository {
        @RequiresApi(Build.VERSION_CODES.O)
        override suspend fun getEvents(): List<Event> {
            return listOf(
                Event(
                    1,
                    "Consulta Médica",
                    "Levar documento e cartão SUS",
                    "http://google.com",
                    EventTypeEnum.MEETING,
                    "11",
                    LocalTime.parse("08:30"),
                    LocalTime.parse("09:00"),
                    false,
                    false,
                    "2024-06-11T00:00:00Z"
                )
            )
        }

        override suspend fun insert(event: Event) {
            // No-op
        }
    }
}