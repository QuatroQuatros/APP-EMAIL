package com.example.challengelocaweb.domain.repository

import com.example.challengelocaweb.domain.model.Event

interface EventRepository {

    suspend fun insert(event: Event)

    suspend fun getEvents(): List<Event>

    suspend fun delete(event: Event)
}