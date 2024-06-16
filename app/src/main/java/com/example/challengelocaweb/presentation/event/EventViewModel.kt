package com.example.challengelocaweb.presentation.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel(){
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events
    init {
        fetchEvents()
    }

    fun fetchEvents()
    {
        viewModelScope.launch {
            _events.value = eventRepository.getEvents()
        }
    }

    fun saveEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.insert(event)
            fetchEvents()
        }
    }

}



