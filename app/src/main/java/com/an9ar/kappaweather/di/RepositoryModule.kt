package com.an9ar.kappaweather.di

import com.an9ar.kappaweather.data.repository.LocationRepositoryImpl
import com.an9ar.kappaweather.data.repository.WeatherRepositoryImpl
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideLocationRepository(impl: LocationRepositoryImpl): LocationRepository
    @Binds
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository
}