package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.retrofit_result.Result
import retrofit2.http.GET

interface CitiesApi {
    @GET("cities.json")
    suspend fun getCitiesList(): Result<List<CityDTO>>
}