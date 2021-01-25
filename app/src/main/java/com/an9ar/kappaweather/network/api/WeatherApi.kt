package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.BuildConfig
import com.an9ar.kappaweather.network.dto.CurrentWeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val SERVER_TOKEN = BuildConfig.WEATHER_SERVER_TOKEN

interface WeatherApi {
    @GET("weather?units=metric&lang=ru")
    suspend fun getCurrentWeatherByGeo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") token: String = SERVER_TOKEN
    ): Response<CurrentWeatherDTO>
}