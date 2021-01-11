package com.an9ar.kappaweather.network.dto

import com.an9ar.kappaweather.data.models.CityModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDTO (
    @SerialName("city") val city: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double,
    @SerialName("country") val country: String
)

fun CityDTO.toCityModel(): CityModel {
    return CityModel(
        city = this.city,
        lat = this.lat,
        lng = this.lng,
        country = this.country
    )
}