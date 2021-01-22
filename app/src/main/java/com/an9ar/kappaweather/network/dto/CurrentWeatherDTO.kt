package com.an9ar.kappaweather.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    @SerialName("coord") val coordinates: CoordinatesDTO,
    @SerialName("weather") val weather: List<WeatherInformationDTO>,
    @SerialName("base") val base: String,
    @SerialName("main") val mainInformation: MainInformationDTO,
    @SerialName("visibility") val visibility: Long,
    @SerialName("wind") val wind: WindInformationDTO,
    @SerialName("clouds") val clouds: CloudsInformationDTO,
    @SerialName("dt") val time: Long,
    @SerialName("sys") val location: LocationInformationDTO,
    @SerialName("timezone") val timezone: Long,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("cod") val code: Int
)

@Serializable
data class CoordinatesDTO(
    @SerialName("lon") val longitude: Double,
    @SerialName("lat") val latitude: Double
)

@Serializable
data class WeatherInformationDTO(
    @SerialName("id") val id: Long,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)

@Serializable
data class MainInformationDTO(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feels_like: Double,
    @SerialName("temp_min") val temp_min: Double,
    @SerialName("temp_max") val temp_max: Double,
    @SerialName("pressure") val pressure: Long,
    @SerialName("humidity") val humidity: Long,
    @SerialName("sea_level") val sea_level: Long,
    @SerialName("grnd_level") val grnd_level: Long
)

@Serializable
data class WindInformationDTO(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Long
)

@Serializable
data class CloudsInformationDTO(
    @SerialName("all") val all: Int
)

@Serializable
data class LocationInformationDTO(
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long
)