package com.an9ar.kappaweather.data.db.dao

import androidx.room.*
import com.an9ar.kappaweather.data.db.entity.CityEntity

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cityentity")
    suspend fun getDepartmentsList(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cityList: List<CityEntity>)

    @Update
    suspend fun update(city: CityEntity)

    @Delete
    suspend fun delete(city: CityEntity)

    @Query("DELETE FROM cityentity")
    suspend fun clearTable()

}