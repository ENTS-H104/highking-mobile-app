// data/repository/MountainRepository.kt
package com.entsh104.highking.data.repository

import androidx.lifecycle.LiveData
import com.entsh104.highking.data.source.local.dao.MountainDao
import com.entsh104.highking.data.source.local.entity.MountainEntity

class MountainRepository(private val mountainDao: MountainDao) {

    fun getFavoriteMountains(): LiveData<List<MountainEntity>> = mountainDao.getFavoriteMountains()

    suspend fun insertMountain(mountain: MountainEntity) = mountainDao.insertMountain(mountain)

    suspend fun deleteMountain(mountain: MountainEntity) = mountainDao.deleteMountain(mountain)

    suspend fun updateMountainFavoriteStatus(mountainId: String, isLoved: Boolean) {
        mountainDao.updateMountainFavoriteStatus(mountainId, isLoved)
    }
}
