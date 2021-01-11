package com.an9ar.kappaweather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val city: String,
    val lat: Double,
    val lng: Double,
    val country: String
)