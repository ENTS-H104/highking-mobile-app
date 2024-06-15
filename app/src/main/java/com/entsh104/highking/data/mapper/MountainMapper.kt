// data/mapper/MountainMapper.kt
package com.entsh104.highking.data.mapper

import com.entsh104.highking.data.model.MountainResponse
import com.entsh104.highking.data.source.local.entity.MountainEntity

object MountainMapper {
    fun mapResponseToEntity(response: MountainResponse, isLoved: Boolean = false): MountainEntity {
        return MountainEntity(
            mountainId = response.mountainId,
            name = response.name,
            imageUrl = response.imageUrl,
            description = response.description,
            height = response.height,
            status = response.status,
            lat = response.lat,
            lon = response.lon,
            magmaCategory = response.magmaCategory,
            province = response.province,
            harga = response.harga,
            gmaps = response.gmaps,
            totalTripOpen = response.totalTripOpen,
            isLoved = isLoved
        )
    }

    fun mapEntityToResponse(entity: MountainEntity): MountainResponse {
        return MountainResponse(
            mountainId = entity.mountainId,
            name = entity.name,
            imageUrl = entity.imageUrl,
            description = entity.description ?: "",
            height = entity.height ?: "",
            status = entity.status ?: "",
            lat = entity.lat ?: 0.0,
            lon = entity.lon ?: 0.0,
            magmaCategory = entity.magmaCategory ?: "",
            province = entity.province ?: "",
            harga = entity.harga ?: 0,
            gmaps = entity.gmaps ?: "",
            totalTripOpen = entity.totalTripOpen ?: 0
        )
    }
}
