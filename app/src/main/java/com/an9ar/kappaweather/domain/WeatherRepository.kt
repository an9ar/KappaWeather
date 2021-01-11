package com.an9ar.kappaweather.domain

import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.Result

interface WeatherRepository {
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
    suspend fun getCitiesList(): Result<List<CityDTO>>

    suspend fun setCitiesList(citiesList: List<CityDTO>)
}