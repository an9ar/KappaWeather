package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.CitiesApi
import com.an9ar.kappaweather.network.api.CountriesApi
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.network.dto.toCountryModel
import com.an9ar.kappaweather.network.retrofit_result.Result
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.network.utils.getResult
import com.an9ar.kappaweather.network.utils.performGetOperation
import com.an9ar.kappaweather.network.utils.performUpdateOperation
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val countriesApi: CountriesApi,
    private val citiesApi: CitiesApi,
    private val countriesDao: CountriesDao,
    private val citiesDao: CitiesDao
) : WeatherRepository {

    override fun getCountriesList(): LiveData<Resource.Status> = performGetOperation(
        networkCall = { getResult { citiesApi.getCountriesList() } },
        saveCallResult = { countriesDao.insertAll(it.results.map { it.toCountryModel() }) }
    )

    override fun updateCountriesList(): LiveData<Resource<List<CountryModel>>> = performUpdateOperation(
        databaseQuery = { countriesDao.getCountriesList() },
        networkCall = { getResult { citiesApi.getCountriesList() } },
        saveCallResult = { countriesDao.insertAll(it.results.map { it.toCountryModel() }) },
    )

    override fun getCitiesList(): LiveData<Resource.Status> = performGetOperation(
        networkCall = { getResult { citiesApi.getCitiesList() } },
        saveCallResult = { citiesDao.insertAll(it.results.map { it.toCityModel() }) }
    )

    override fun updateCitiesList(): LiveData<Resource<List<CityModel>>> = performUpdateOperation(
        databaseQuery = { citiesDao.getCitiesList() },
        networkCall = { getResult { citiesApi.getCitiesList() } },
        saveCallResult = { citiesDao.insertAll(it.results.map { it.toCityModel() }) },
    )

    override suspend fun setCitiesList(citiesList: List<CityDTO>) {
        citiesList.forEach {
            citiesDao.insert(it.toCityModel())
        }
    }
}