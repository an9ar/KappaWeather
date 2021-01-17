package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.utils.Resource

interface LocationRepository {
    fun getCountriesList(): LiveData<Resource.Status>
    fun updateCountriesList(): LiveData<Resource<List<CountryModel>>>

    fun getCitiesList(): LiveData<Resource.Status>
    fun updateCitiesList(): LiveData<Resource<List<CityModel>>>

    suspend fun setCitiesList(citiesList: List<CityDTO>)
}