package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.network.utils.Resource
import kotlinx.coroutines.Deferred

interface WeatherRepository {
    fun getLocationWeather(objectId: Long, objectName: String, latitude: Double, longitude: Double): Deferred<Resource.Status>
    fun insertLocationWeather(weatherModel: WeatherModel)
    fun getLocalLocationsWeather(): LiveData<List<WeatherModel>>
}