package com.an9ar.kappaweather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityModel (
    @PrimaryKey
    val objectId: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    val population: Long,
    val countryId: String
) {
    companion object {
        val EMPTY = CityModel(
            objectId = "",
            lat = 0.0,
            lng = 0.0,
            name = "",
            countryId = "",
            population = 0
        )
    }
}