package com.an9ar.kappaweather.network

import com.an9ar.kappaweather.network.retrofit_result.Result
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface CountriesApi {
    @GET("rest/v2/all")
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
}

data class CountriesListResponse(
        @SerializedName("name") val name: String,
        @SerializedName("flag") val flagUrl: String
)