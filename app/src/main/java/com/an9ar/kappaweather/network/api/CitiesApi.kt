package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.network.dto.CityDTO
import retrofit2.Response
import retrofit2.http.GET

interface CitiesApi {
    @GET("cities.json")
    suspend fun getCitiesList(): Response<List<CityDTO>>
}