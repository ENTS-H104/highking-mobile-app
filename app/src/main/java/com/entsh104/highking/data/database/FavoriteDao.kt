package com.entsh104.highking.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from tb_favorite ORDER BY username ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM tb_favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>
}