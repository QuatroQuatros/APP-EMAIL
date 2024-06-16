package com.example.challengelocaweb.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.model.EventWithParticipants
import com.example.challengelocaweb.domain.model.Participant
import com.example.challengelocaweb.domain.repository.EventRepository

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getEvents(): List<Event>


    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventWithParticipants(eventId: Int): EventWithParticipants

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipant(participant: Participant)

    @Transaction
    @Delete
    suspend fun delete(event: Event)
}