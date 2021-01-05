package com.an9ar.kappaweather.domain

import com.an9ar.kappaweather.network.CountriesListResponse

interface WeatherRepository {
    suspend fun getCountriesList(): List<CountriesListResponse>
}