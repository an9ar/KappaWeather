package com.an9ar.kappaweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.models.CityModel


@Database(
    entities = [
        CityModel::class
    ],
    version = 8
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "KAPPA_DB"
    }

    abstract fun getCitiesDao(): CitiesDao

}