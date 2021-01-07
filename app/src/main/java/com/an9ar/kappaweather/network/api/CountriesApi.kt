package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.network.retrofit_result.Result
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface CountriesApi {
    @GET("rest/v2/all")
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
}

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