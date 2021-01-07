package com.an9ar.kappaweather.data.repository

import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.api.CountriesApi
import com.an9ar.kappaweather.network.api.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.Result
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val countriesApi: CountriesApi
) : WeatherRepository {

    override suspend fun getCountriesList(): Result<List<CountriesListResponse>> {
        return countriesApi.getCountriesList()
    }

}