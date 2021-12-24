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
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val countriesDao: CountriesDao,
    private val citiesDao: CitiesDao
) : LocationRepository {

    override suspend fun getCountriesList(): List<CountryModel> {
        return countriesDao.getCountriesList()
    }

    override suspend fun fetchCountriesList() {
        try {
            val response = locationApi.getCountriesList()
            if (response.isSuccessful) {
                countriesDao.insertAll(response.body()?.results?.map { it.toCountryModel() } ?: emptyList())
            }
        } catch (e: Exception) {

        }
    }

    override suspend fun getCitiesListByCountry(countryId: String): List<CityModel> {
        try {
            val response = locationApi.getCitiesListByCountry(
                whereCondition = """{"country": {"__type": "Pointer","className": "Continentscountriescities_Country","objectId": "$countryId"}}"""
            )
            if (response.isSuccessful) {
                return response.body()?.results?.map { it.toCityModel() } ?: emptyList()
            }
        } catch (e: Exception) {
        }
        return emptyList()
    }

    override suspend fun addLocationCity(city: CityModel) {
        citiesDao.insert(city = city)
    }

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

    override fun getLocationCitiesList(): LiveData<List<CityModel>> {
        return citiesDao.getCitiesList()
    }

    override fun clearLocations() = runBlocking(Dispatchers.IO) {
        citiesDao.clearTable()
    }
}