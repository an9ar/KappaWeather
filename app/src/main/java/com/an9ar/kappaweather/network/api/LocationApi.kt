package com.an9ar.kappaweather.network.api

import com.an9ar.kappaweather.BuildConfig
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountryDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocationApi {
    @Headers(
        "X-Parse-Application-Id: ${BuildConfig.X_Parse_Application_Id}",
        "X-Parse-REST-API-Key: ${BuildConfig.X_Parse_REST_API_Key}"
    )
    @GET("Continentscountriescities_City")
    suspend fun getCitiesList(
        /*@Query("limit") limit: Int = 10*/
    ): Response<CitiesListResponse>

    @Headers(
        "X-Parse-Application-Id: ${BuildConfig.X_Parse_Application_Id}",
        "X-Parse-REST-API-Key: ${BuildConfig.X_Parse_REST_API_Key}"
    )
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