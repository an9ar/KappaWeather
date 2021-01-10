package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.Result
import retrofit2.http.GET

interface CountriesApi {
    @GET("rest/v2/all")
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
}