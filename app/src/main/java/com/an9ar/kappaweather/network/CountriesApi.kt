package com.an9ar.kappaweather.network

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CountriesApi {
    @GET("rest/v2/all")
    fun getCountriesList(): Deferred<List<CountriesListResponse>>
}

data class CountriesListResponse(
        @SerializedName("name") val name: String,
        @SerializedName("flag") val flagUrl: String
)