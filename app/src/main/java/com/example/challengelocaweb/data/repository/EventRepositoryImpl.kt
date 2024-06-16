package com.example.challengelocaweb.data.repository

import com.example.challengelocaweb.domain.dao.EventDao
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
) : EventRepository
{
    override suspend fun getEvents(): List<Event> {
        return eventDao.getEvents()
    }

    override suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    override suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

}