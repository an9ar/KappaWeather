package com.an9ar.kappaweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.dao.CountriesDao
import com.an9ar.kappaweather.data.db.dao.WeatherDao
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.data.models.WeatherModel


@Database(
    entities = [
        CityModel::class,
        CountryModel::class,
        WeatherModel::class
    ],
    version = 15,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "KAPPA_DB"
    }

    abstract fun getCitiesDao(): CitiesDao
    abstract fun getCountriesDao(): CountriesDao
    abstract fun getWeatherDao(): WeatherDao

}