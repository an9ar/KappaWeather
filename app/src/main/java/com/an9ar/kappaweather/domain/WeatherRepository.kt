package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.network.utils.Resource

interface WeatherRepository {
    fun getlocationWeather(objectId: String, latitude: Double, longitude: Double): LiveData<Resource<WeatherModel>>
}