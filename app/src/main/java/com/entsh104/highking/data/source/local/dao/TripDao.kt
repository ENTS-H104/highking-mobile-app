// data/source/local/dao/TripDao.kt
package com.entsh104.highking.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.entsh104.highking.data.source.local.entity.TripEntity

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrip(trip: TripEntity)

    @Delete
    suspend fun deleteTrip(trip: TripEntity)

    @Query("SELECT * FROM trips")
    fun getFavoriteTrips(): LiveData<List<TripEntity>>
}
