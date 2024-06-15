// data/viewmodel/TripViewModel.kt
package com.entsh104.highking.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.entsh104.highking.data.repository.TripRepository
import com.entsh104.highking.data.source.local.AppDatabase
import com.entsh104.highking.data.source.local.entity.TripEntity
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TripRepository
    val favoriteTrips: LiveData<List<TripEntity>>

    init {
        val tripDao = AppDatabase.getInstance(application).tripDao()
        repository = TripRepository(tripDao)
        favoriteTrips = repository.getFavoriteTrips()
    }

    fun insertTrip(trip: TripEntity) = viewModelScope.launch {
        repository.insertTrip(trip)
    }

    fun deleteTrip(trip: TripEntity) = viewModelScope.launch {
        repository.deleteTrip(trip)
    }
}
