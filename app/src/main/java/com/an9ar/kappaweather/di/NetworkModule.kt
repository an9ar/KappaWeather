package com.an9ar.kappaweather.di

import com.an9ar.kappaweather.BuildConfig
import com.an9ar.kappaweather.network.api.CitiesApi
import com.an9ar.kappaweather.network.api.CountriesApi
import com.an9ar.kappaweather.network.retrofit_result.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideCountriesApi(): CountriesApi {
        val httpClient = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("X-Parse-Application-Id","mOITO6uEQyMcX9uXIUZ5gbjcjaAlPKcl4nfuTD46")
                            .addHeader("X-Parse-REST-API-Key","b1tqOKQJ9gTyiJsbQYdj5yuXRvLyuaxM4MdgAPpD")
                            .method(chain.request().method, chain.request().body)
                            .build()
                        return@addInterceptor chain.proceed(request)
                    }
                }
            }
            .build()

        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.COUNTRIES_SERVER_URL)
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
            .create(CountriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCitiesApi(): CitiesApi {
        val httpClient = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()

        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_STORAGE_URL)
            .addCallAdapterFactory(ResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(CitiesApi::class.java)
    }
}