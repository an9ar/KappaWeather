package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.network.utils.Resource

interface LocationRepository {
    suspend fun getCountriesList(): List<CountryModel>
    suspend fun fetchCountriesList()
    suspend fun getCitiesListByCountry(countryId: String): List<CityModel>
    suspend fun addLocationCity(city: CityModel)

    fun getCitiesListBySearch(countryId: String, searchQuery: String): LiveData<Resource<List<CityModel>>>

    fun getLocationCitiesList(): LiveData<List<CityModel>>
    fun clearLocations()
}