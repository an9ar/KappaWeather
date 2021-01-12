package com.an9ar.kappaweather.network.dto

import com.an9ar.kappaweather.data.models.CountryModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDTO (
    @SerialName("objectId") val objectId: String,
    @SerialName("name") val name: String
)

fun CountryDTO.toCountryModel(): CountryModel {
    return CountryModel(
        objectId = this.objectId,
        name = this.name
    )
}