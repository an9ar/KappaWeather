package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.WeatherDao
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.api.WeatherApi
import com.an9ar.kappaweather.network.dto.toWeatherModel
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.network.utils.getResult
import com.an9ar.kappaweather.network.utils.performUpdateOperation
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getlocationWeather(
        objectId: String,
        latitude: Double,
        longitude: Double
    ): LiveData<Resource<WeatherModel>> = performUpdateOperation(
        databaseQuery = { weatherDao.getLocationById(locationId = objectId) },
        networkCall = {
            getResult {
                weatherApi.getCurrentWeatherByGeo(
                    latitude = latitude,
                    longitude = longitude
                )
            }
        },
        saveCallResult = {
            log("RESULT - $it")
            log("RESULT (model) - ${it.toWeatherModel(objectId)}")
            weatherDao.insert(it.toWeatherModel(objectId = objectId))
        }
    )

}