package com.an9ar.kappaweather.data

import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.CountriesApi
import com.an9ar.kappaweather.network.CountriesListResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val countriesApi: CountriesApi
) : WeatherRepository {

    override suspend fun getCountriesList(): List<CountriesListResponse> {
        return countriesApi.getCountriesList()
    }

}