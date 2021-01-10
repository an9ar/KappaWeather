package com.an9ar.kappaweather.network.dto

import com.an9ar.kappaweather.data.models.CountryModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountriesListResponse(
    @SerialName("name") val name: String,
    @SerialName("flag") val flagUrl: String
)

fun CountriesListResponse.toCountryModel(): CountryModel {
    return CountryModel(
        name = this.name,
        flagUrl = this.flagUrl
    )
}