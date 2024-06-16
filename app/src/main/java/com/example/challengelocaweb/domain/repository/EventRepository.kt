package com.example.challengelocaweb.domain.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.challengelocaweb.domain.model.Event

@Dao
interface EventRepository {
    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<Event>
}