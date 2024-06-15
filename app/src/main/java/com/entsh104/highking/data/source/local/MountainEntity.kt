package com.entsh104.highking.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mountains")
data class MountainEntity(
    @PrimaryKey val mountainId: String,
    val name: String,
    val imageUrl: String,
    val description: String? = null,
    val height: String? = null,
    val status: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val magmaCategory: String? = null,
    val province: String? = null,
    val harga: Int? = null,
    val gmaps: String? = null,
    val totalTripOpen: Int? = null,
    var isLoved: Boolean = false
)
