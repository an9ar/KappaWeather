package com.an9ar.kappaweather.data.repository

import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.network.api.LocationApi
import com.an9ar.kappaweather.network.dto.toCityModel
import com.an9ar.kappaweather.network.dto.toCountryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val countriesDao: CountriesDao,
    private val citiesDao: CitiesDao
) : LocationRepository {

    override fun getCountriesList(): Flow<List<CountryModel>> {
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

    override suspend fun getCitiesListBySearch(
        countryId: String,
        searchQuery: String
    ): List<CityModel> {
        try {
            val response = locationApi.getCitiesListBySearch(
                whereCondition = """{"country": {"__type": "Pointer","className": "Continentscountriescities_Country","objectId": "$countryId"}, "name": {"${'$'}regex": "${searchQuery.capitalize()}"}}"""
            )
            if (response.isSuccessful) {
                return response.body()?.results?.map { it.toCityModel() }?.sortedBy { it.name } ?: emptyList()
            }
        } catch (e: Exception) {
        }
        return emptyList()
    }

    override suspend fun addLocationCity(city: CityModel) {
        citiesDao.insert(city = city)
    }

    override fun getLocationCitiesList(): Flow<List<CityModel>> {
        return citiesDao.getCitiesList()
    }

    override suspend fun clearLocations() {
        citiesDao.clearTable()
    }
}