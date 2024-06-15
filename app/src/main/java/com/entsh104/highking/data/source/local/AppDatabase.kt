// data/source/local/AppDatabase.kt
package com.entsh104.highking.data.source.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.entsh104.highking.data.source.local.dao.MountainDao
import com.entsh104.highking.data.source.local.dao.TripDao
import com.entsh104.highking.data.source.local.entity.MountainEntity
import com.entsh104.highking.data.source.local.entity.TripEntity

@Database(entities = [MountainEntity::class, TripEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun mountainDao(): MountainDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "highking_favorite_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
