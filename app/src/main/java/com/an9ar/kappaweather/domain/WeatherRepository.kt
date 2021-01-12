package com.an9ar.kappaweather.domain

import androidx.lifecycle.LiveData
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.Result
import com.an9ar.kappaweather.network.utils.Resource

interface WeatherRepository {
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
    fun getCitiesList(): LiveData<Resource<List<CityModel>>>

    suspend fun setCitiesList(citiesList: List<CityDTO>)
}