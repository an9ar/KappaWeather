package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountryDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CitiesApi {
    @GET("Continentscountriescities_City")
    suspend fun getCitiesList(
        @Query("limit") limit: Int = 10
    ): Response<CitiesListResponse>

    @Headers(
        "X-Parse-Application-Id: mOITO6uEQyMcX9uXIUZ5gbjcjaAlPKcl4nfuTD46",
        "X-Parse-REST-API-Key: b1tqOKQJ9gTyiJsbQYdj5yuXRvLyuaxM4MdgAPpD"
    )
    @GET("Continentscountriescities_Country")
    suspend fun getCountriesList(
        @Query("limit") limit: Int = 10
    ): Response<CountriesListResponse>
}

data class CitiesListResponse(
    val results: List<CityDTO>
)

data class CountriesListResponse(
    val results: List<CountryDTO>
)