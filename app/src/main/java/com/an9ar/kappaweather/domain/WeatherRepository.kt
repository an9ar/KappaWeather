package com.an9ar.kappaweather.domain

import com.an9ar.kappaweather.network.api.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.Result

interface WeatherRepository {
    suspend fun getCountriesList(): Result<List<CountriesListResponse>>
}