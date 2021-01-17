package com.an9ar.kappaweather.di

import com.an9ar.kappaweather.data.repository.LocationRepositoryImpl
import com.an9ar.kappaweather.domain.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideWeatherRepository(impl: LocationRepositoryImpl): LocationRepository
}