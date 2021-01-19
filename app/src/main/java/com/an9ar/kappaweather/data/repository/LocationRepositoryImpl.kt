package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.network.api.LocationApi
import com.an9ar.kappaweather.network.dto.CityCountryDTO
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.network.dto.toCountryModel
import com.an9ar.kappaweather.network.utils.*
import com.an9ar.kappaweather.network.utils.performFetchOperation
import java.net.URLEncoder
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val countriesDao: CountriesDao,
    private val citiesDao: CitiesDao
) : LocationRepository {

    override fun getCountriesList(): LiveData<Resource.Status> = performFetchOperation(
        networkCall = { getResult { locationApi.getCountriesList() } },
        saveCallResult = { countriesDao.insertAll(it.results.map { it.toCountryModel() }) }
    )

    override fun updateCountriesList(): LiveData<Resource<List<CountryModel>>> = performUpdateOperation(
        databaseQuery = { countriesDao.getCountriesList() },
        networkCall = { getResult { locationApi.getCountriesList() } },
        saveCallResult = { countriesDao.insertAll(it.results.map { it.toCountryModel() }) },
    )

    override fun getCitiesList(countryId: String): LiveData<Resource<List<CityModel>>> = performGetOperation(
        databaseQuery = { citiesDao.getCitiesList() },
        networkCall = {
            getResult {
                locationApi.getCitiesList(
                        countryDTO = """{"country": {"__type": "Pointer","className": "Continentscountriescities_Country","objectId": "$countryId"}}"""
                )
            }
        },
        saveCallResult = { citiesDao.insertAll(it.results.map { it.toCityModel() }) }
    )

    /*override fun updateCitiesList(countryId: String): LiveData<Resource<List<CityModel>>> = performUpdateOperation(
        databaseQuery = { citiesDao.getCitiesList() },
        networkCall = {
            getResult {
                locationApi.getCitiesList(
                    countryDTO = CityCountryDTO(
                        type = "Pointer",
                        className = "Continentscountriescities_Country",
                        objectId = countryId
                    )
                )
            }
        },
        saveCallResult = { citiesDao.insertAll(it.results.map { it.toCityModel() }) },
    )*/

    override suspend fun setCitiesList(citiesList: List<CityDTO>) {
        citiesList.forEach {
            citiesDao.insert(it.toCityModel())
        }
    }
}