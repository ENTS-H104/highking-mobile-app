// data/viewmodel/TripsViewModel.kt
package com.entsh104.highking.data.viewmodel

import ApiService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.source.remote.TripPagingSource
import kotlinx.coroutines.flow.Flow

class TripsViewModel(private val apiService: ApiService) : ViewModel() {

    val trips: Flow<PagingData<TripFilter>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { TripPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}

class TripsViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
