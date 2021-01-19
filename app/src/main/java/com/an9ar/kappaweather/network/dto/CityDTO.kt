package com.an9ar.kappaweather.network.dto

import com.an9ar.kappaweather.data.models.CityModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDTO(
        @SerialName("objectId") val objectId: String,
        @SerialName("location") val location: CityLocationDTO,
        @SerialName("country") val country: CityCountryDTO,
        @SerialName("name") val name: String,
        @SerialName("population") val population: Long
)

@Serializable
data class CityCountryDTO(
        @SerialName("__type") val type: String,
        @SerialName("className") val className: String,
        @SerialName("objectId") val objectId: String
)

@Serializable
data class CityLocationDTO(
        @SerialName("__type") val type: String,
        @SerialName("latitude") val latitude: Double,
        @SerialName("longitude") val longitude: Double
)

fun CityDTO.toCityModel(): CityModel {
    return CityModel(
            id = null,
            objectId = this.objectId,
            lat = this.location.latitude,
            lng = this.location.longitude,
            name = this.name,
            population = this.population
    )
}