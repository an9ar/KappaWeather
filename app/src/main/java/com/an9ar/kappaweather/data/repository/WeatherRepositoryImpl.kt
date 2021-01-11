package com.an9ar.kappaweather.data.repository

import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.CitiesApi
import com.an9ar.kappaweather.network.api.CountriesApi
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.dto.toCityEntity
import com.an9ar.kappaweather.network.retrofit_result.Result
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val countriesApi: CountriesApi,
        private val citiesApi: CitiesApi,
        private val citiesDao: CitiesDao
) : WeatherRepository {

    override suspend fun getCountriesList(): Result<List<CountriesListResponse>> {
        return countriesApi.getCountriesList()
    }

    override suspend fun getCitiesList(): Result<List<CityDTO>> {
        return citiesApi.getCitiesList()
    }

    private fun refreshCitiesList() {
        
    }

    override suspend fun setCitiesList(citiesList: List<CityDTO>) {
        citiesList.forEach {
            citiesDao.insert(it.toCityEntity())
        }
    }
}