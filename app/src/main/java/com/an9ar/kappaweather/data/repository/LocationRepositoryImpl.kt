package com.an9ar.kappaweather.data.repository

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.api.LocationApi
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.network.dto.toCountryModel
import com.an9ar.kappaweather.network.utils.*
import com.an9ar.kappaweather.network.utils.performFetchOperation
import kotlinx.coroutines.*
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

    override fun updateCountriesList(): LiveData<Resource<List<CountryModel>>> =
        performUpdateOperation(
            databaseQuery = { countriesDao.getCountriesList() },
            networkCall = { getResult { locationApi.getCountriesList() } },
            saveCallResult = { countriesDao.insertAll(it.results.map { it.toCountryModel() }) },
        )

    override fun getCitiesListByCountry(countryId: String): LiveData<Resource<List<CityModel>>> =
        performGetNetworkOperation(
            networkCall = {
                getResult {
                    locationApi.getCitiesListByCountry(
                        whereCondition = """{"country": {"__type": "Pointer","className": "Continentscountriescities_Country","objectId": "$countryId"}}"""
                    )
                }
            },
            convertResponseTo = { response ->
                response.results
                    .map { it.toCityModel() }
                    .sortedBy { it.name }
            }
        )

    override fun getCitiesListBySearch(
        countryId: String,
        searchQuery: String
    ): LiveData<Resource<List<CityModel>>> = performGetNetworkOperation(
        networkCall = {
            getResult {
                locationApi.getCitiesListBySearch(
                    whereCondition = """{"country": {"__type": "Pointer","className": "Continentscountriescities_Country","objectId": "$countryId"}, "name": {"${'$'}regex": "${searchQuery.capitalize()}"}}"""
                )
            }
        },
        convertResponseTo = { response ->
            log("response - $response")
            response.results
                .map { it.toCityModel() }
                .sortedBy { it.name }
        }
    )

    override fun addLocationCity(city: CityModel) = runBlocking(Dispatchers.IO) {
        citiesDao.insert(city = city)
    }

    override fun getLocationCitiesList(): LiveData<List<CityModel>> {
        return citiesDao.getCitiesList()
    }

    override fun clearLocations() = runBlocking(Dispatchers.IO) {
        citiesDao.clearTable()
    }
}