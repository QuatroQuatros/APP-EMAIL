package com.example.challengelocaweb.presentation.event

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.repository.EventRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
open class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    application: Application
): AndroidViewModel(application){

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> get() = _currentLocation
    init {
        fetchEvents()
        fetchCurrentLocation()
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

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModelScope.launch {
                        _currentLocation.value = getAddressFromLocation(it)
                    }
                }
            }
            .addOnFailureListener {
                _currentLocation.value = "Failed to get location"
            }
    }

    private fun getAddressFromLocation(location: Location): String {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return if (addresses != null) {
            val address = addresses[0]
            "${address.thoroughfare}, ${address.locality}, ${address.postalCode}, ${address.countryName}"
        } else {
            "Address not found"
        }
    }


}



