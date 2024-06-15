// data/mapper/TripMapper.kt
package com.entsh104.highking.data.mapper

import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.source.local.entity.TripEntity

object TripMapper {
    fun mapFilterToEntity(filter: TripFilter): TripEntity {
        return TripEntity(
            openTripUuid = filter.open_trip_uuid,
            name = filter.name,
            imageUrl = filter.image_url,
            price = filter.price,
            mountainName = filter.mountain_name,
            mountainUuid = filter.mountain_uuid,
            totalParticipants = filter.total_participants,
            minPeople = filter.min_people,
            maxPeople = filter.max_people
        )
    }

    fun mapEntityToFilter(entity: TripEntity): TripFilter {
        return TripFilter(
            open_trip_uuid = entity.openTripUuid,
            name = entity.name,
            image_url = entity.imageUrl,
            price = entity.price,
            mountain_name = entity.mountainName,
            mountain_uuid = entity.mountainUuid,
            total_participants = entity.totalParticipants,
            min_people = entity.minPeople,
            max_people = entity.maxPeople
        )
    }
}
