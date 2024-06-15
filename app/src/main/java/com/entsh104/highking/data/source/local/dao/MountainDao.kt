// data/source/local/dao/MountainDao.kt
package com.entsh104.highking.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.entsh104.highking.data.source.local.entity.MountainEntity

@Dao
interface MountainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMountain(mountain: MountainEntity)

    @Delete
    suspend fun deleteMountain(mountain: MountainEntity)

    @Query("SELECT * FROM mountains WHERE isLoved = 1")
    fun getFavoriteMountains(): LiveData<List<MountainEntity>>

    @Query("UPDATE mountains SET isLoved = :isLoved WHERE mountainId = :mountainId")
    suspend fun updateMountainFavoriteStatus(mountainId: String, isLoved: Boolean)
}
