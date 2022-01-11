package com.an9ar.kappaweather.data.repository

import com.an9ar.kappaweather.data.db.dao.WeatherDao
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.WeatherApi
import com.an9ar.kappaweather.network.dto.toWeatherModel
import kotlinx.coroutines.flow.Flow
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

    override fun getLocalLocationsWeather(): Flow<List<WeatherModel>> = weatherDao.getLocationsList()

    override suspend fun clearLocationsWeather() {
        weatherDao.clearTable()
    }

}