package com.an9ar.kappaweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.an9ar.kappaweather.data.db.dao.CitiesDao
import com.an9ar.kappaweather.data.db.entity.CityEntity


@Database(
    entities = [
        CityEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "KAPPA_DB"
    }

    abstract fun getCitiesDao(): CitiesDao

    /*abstract fun getFacultyDao(): FacultyDao
    abstract fun getDepartmentDao(): DepartmentDao
    abstract fun getStaffDao(): StaffDao
    abstract fun getScheduleDao(): ScheduleDao*/

}