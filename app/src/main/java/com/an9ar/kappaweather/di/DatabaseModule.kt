package com.an9ar.kappaweather.di

import android.content.Context
import androidx.room.Room
import com.an9ar.kappaweather.data.db.AppDatabase
import com.an9ar.kappaweather.data.db.dao.CitiesDao
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
    fun provideFacultyDao(db: AppDatabase): CitiesDao = db.getCitiesDao()
}