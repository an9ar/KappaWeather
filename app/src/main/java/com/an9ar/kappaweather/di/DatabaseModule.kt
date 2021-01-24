package com.an9ar.kappaweather.di

import android.content.Context
import androidx.room.Room
import com.an9ar.kappaweather.data.db.AppDatabase
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.db.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideCitiesDao(db: AppDatabase): CitiesDao = db.getCitiesDao()

    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase): CountriesDao = db.getCountriesDao()

    @Singleton
    @Provides
    fun provideWeatherDao(db: AppDatabase): WeatherDao = db.getWeatherDao()
}