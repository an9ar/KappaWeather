package com.an9ar.kappaweather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val city: String,
    val lat: Double,
    val lng: Double,
    val country: String
)