package com.entsh104.highking.data.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.entsh104.highking.data.database.Favorite
import com.entsh104.highking.data.repository.FavoriteRepository

class FavoritesViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun getUserFavorite(mountainUuid: String): LiveData<Favorite> =
        mFavoriteRepository.getUserFavorite(mountainUuid)
}