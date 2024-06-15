// data/viewmodel/MountainViewModel.kt
package com.entsh104.highking.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.entsh104.highking.data.repository.MountainRepository
import com.entsh104.highking.data.source.local.AppDatabase
import com.entsh104.highking.data.source.local.entity.MountainEntity
import kotlinx.coroutines.launch

class MountainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MountainRepository
    val favoriteMountains: LiveData<List<MountainEntity>>

    init {
        val mountainDao = AppDatabase.getInstance(application).mountainDao()
        repository = MountainRepository(mountainDao)
        favoriteMountains = repository.getFavoriteMountains()
    }

    fun insertMountain(mountain: MountainEntity) = viewModelScope.launch {
        repository.insertMountain(mountain)
    }

    fun deleteMountain(mountain: MountainEntity) = viewModelScope.launch {
        repository.deleteMountain(mountain)
    }
}
