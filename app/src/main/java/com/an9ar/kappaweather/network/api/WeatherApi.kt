package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.network.dto.CurrentWeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?units=metric&lang=ru")
    fun getCurrentWeatherByGeo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") token: String
    ): Response<CurrentWeatherDTO>
}