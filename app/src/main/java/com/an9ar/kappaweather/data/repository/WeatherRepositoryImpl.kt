package com.an9ar.kappaweather.data.repository

import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.CitiesApi
import com.an9ar.kappaweather.network.api.CountriesApi
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.network.retrofit_result.Result
import com.an9ar.kappaweather.network.utils.getResult
import com.an9ar.kappaweather.network.utils.performGetOperation
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val countriesApi: CountriesApi,
        private val citiesApi: CitiesApi,
        private val citiesDao: CitiesDao
) : WeatherRepository {

    override suspend fun getCountriesList(): Result<List<CountriesListResponse>> {
        return countriesApi.getCountriesList()
    }

    override fun getCitiesList() = performGetOperation(
            databaseQuery = { citiesDao.getCitiesList() },
            networkCall = { getResult { citiesApi.getCitiesList() } },
            saveCallResult = { citiesDao.insertAll(it.map { it.toCityModel() }) },
    )

    override suspend fun setCitiesList(citiesList: List<CityDTO>) {
        citiesList.forEach {
            citiesDao.insert(it.toCityModel())
        }
    }
}