package com.an9ar.kappaweather.domain

import com.an9ar.kappaweather.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchSelectedLocationWeather(objectId: Long, objectName: String, latitude: Double, longitude: Double)
    fun insertLocationWeather(weatherModel: WeatherModel)
    fun getLocalLocationsWeather(): Flow<List<WeatherModel>>
}