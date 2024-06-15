// data/repository/TripRepository.kt
package com.entsh104.highking.data.repository

import androidx.lifecycle.LiveData
import com.entsh104.highking.data.source.local.dao.TripDao
import com.entsh104.highking.data.source.local.entity.TripEntity

class TripRepository(private val tripDao: TripDao) {

    fun getFavoriteTrips(): LiveData<List<TripEntity>> = tripDao.getFavoriteTrips()

    suspend fun insertTrip(trip: TripEntity) = tripDao.insertTrip(trip)

    suspend fun deleteTrip(trip: TripEntity) = tripDao.deleteTrip(trip)
}
