package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.BuildConfig
import com.an9ar.kappaweather.network.dto.CityCountryDTO
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountryDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val HEADER_APPLICATION_ID = "X-Parse-Application-Id: ${BuildConfig.X_Parse_Application_Id}"
private const val HEADER_CLIENT_KEY = "X-Parse-REST-API-Key: ${BuildConfig.X_Parse_REST_API_Key}"

interface LocationApi {
    @Headers(HEADER_APPLICATION_ID, HEADER_CLIENT_KEY)
    @GET("Continentscountriescities_City?order=name")
    suspend fun getCitiesList(
        @Query("limit") limit: Int = 5000,
        @Query("where") countryDTO: String
    ): Response<CitiesListResponse>

    @Headers(HEADER_APPLICATION_ID, HEADER_CLIENT_KEY)
    @GET("Continentscountriescities_Country?order=name")
    suspend fun getCountriesList(
        @Query("limit") limit: Int = 250
    ): Response<CountriesListResponse>
}

data class CitiesListResponse(
    val results: List<CityDTO>
)

data class CountriesListResponse(
    val results: List<CountryDTO>
)