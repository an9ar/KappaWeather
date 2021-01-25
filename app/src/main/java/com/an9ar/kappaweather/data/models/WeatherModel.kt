package com.an9ar.kappaweather.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherModel (
    @PrimaryKey
    val locationId: String,
    val coordinates: CoordinatesModel,
    val weather: List<WeatherInformationModel>,
    val base: String,
    val mainInformation: MainInformationModel,
    val visibility: Long,
    val wind: WindInformationModel,
    val clouds: CloudsInformationModel,
    val time: Long,
    val location: LocationInformationModel,
    val timezone: Long,
    val id: Long,
    val name: String,
    val code: Int
)

data class CoordinatesModel(
    val longitude: Double,
    val latitude: Double
)

data class WeatherInformationModel(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class MainInformationModel(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val humidity: Long,
    val sea_level: Long,
    val grnd_level: Long
)

data class WindInformationModel(
    val speed: Double,
    val deg: Long
)

data class CloudsInformationModel(
    val all: Int
)

data class LocationInformationModel(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)