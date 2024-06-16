package com.example.challengelocaweb.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.challengelocaweb.presentation.event.components.EventTypeEnum
import java.time.LocalTime

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val link: String,
    val eventType: EventTypeEnum,
    val day: String,
    val location: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val isUnique: Boolean,
    val isAllDay: Boolean,
    val isNotifiable: Boolean,
    val createdAt: String
)

