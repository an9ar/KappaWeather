package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.WeatherDao
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.api.WeatherApi
import com.an9ar.kappaweather.network.dto.toWeatherModel
import com.an9ar.kappaweather.network.utils.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getLocationWeather(
        objectId: Long,
        objectName: String,
        latitude: Double,
        longitude: Double
    ): Deferred<Resource.Status> = performAsyncGetAndInsertOperation(
        networkCall = {
            getResult {
                weatherApi.getCurrentWeatherByGeo(
                    latitude = latitude,
                    longitude = longitude
                )
            }
        },
        saveCallResult = { response ->
            log("WEATHER RESPONSE - $response")
            weatherDao.insert(response.toWeatherModel(objectId = objectId, objectName = objectName))
        }
    )

    override fun insertLocationWeather(weatherModel: WeatherModel) = runBlocking(Dispatchers.IO) {
        weatherDao.insert(location = weatherModel)
    }

    override fun getLocalLocationsWeather(): LiveData<List<WeatherModel>> {
        return weatherDao.getLocationsList()
    }

}