package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.WeatherDao
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.WeatherApi
import com.an9ar.kappaweather.network.dto.toWeatherModel
import com.an9ar.kappaweather.network.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun fetchSelectedLocationWeather(
        objectId: Long,
        objectName: String,
        latitude: Double,
        longitude: Double
    ) {
        try {
            val response = weatherApi.getCurrentWeatherByGeo(
                latitude = latitude,
                longitude = longitude
            )
            if (response.isSuccessful) {
                weatherDao.insert(response.body().toWeatherModel(objectId = objectId, objectName = objectName))
            }
        } catch (e: Exception) {

        }
    }

    override fun insertLocationWeather(weatherModel: WeatherModel) = runBlocking(Dispatchers.IO) {
        weatherDao.insert(location = weatherModel)
    }

    override fun getLocalLocationsWeather(): LiveData<Resource<List<WeatherModel>>> = performGetDBOperation (
        databaseQuery = {
            weatherDao.getLocationsList()
        }
    )

}