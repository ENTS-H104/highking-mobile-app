// data/source/local/entity/TripEntity.kt
package com.entsh104.highking.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val openTripUuid: String,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val mountainName: String,
    val mountainUuid: String,
    val totalParticipants: String?,
    val minPeople: Int?,
    val maxPeople: Int?,
)
