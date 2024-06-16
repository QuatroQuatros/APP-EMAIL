package com.example.challengelocaweb.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.challengelocaweb.domain.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<Event>

    @Insert
    suspend fun insert(event: Event)
}