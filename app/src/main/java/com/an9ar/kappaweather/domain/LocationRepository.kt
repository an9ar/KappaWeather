package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCountriesList(): Flow<List<CountryModel>>
    suspend fun fetchCountriesList()
    suspend fun getCitiesListByCountry(countryId: String): List<CityModel>
    suspend fun getCitiesListBySearch(countryId: String, searchQuery: String): List<CityModel>
    suspend fun addLocationCity(city: CityModel)

    fun getLocationCitiesList(): LiveData<List<CityModel>>
    fun clearLocations()
}