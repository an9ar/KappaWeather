package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.network.utils.Resource

interface WeatherRepository {
    suspend fun fetchSelectedLocationWeather(objectId: Long, objectName: String, latitude: Double, longitude: Double)
    fun insertLocationWeather(weatherModel: WeatherModel)
    fun getLocalLocationsWeather(): LiveData<Resource<List<WeatherModel>>>
}