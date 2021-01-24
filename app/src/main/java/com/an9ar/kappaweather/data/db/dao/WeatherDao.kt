package com.an9ar.kappaweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.an9ar.kappaweather.data.models.WeatherModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weathermodel")
    fun getLocationsList(): LiveData<List<WeatherModel>>

    @Query("SELECT * FROM weathermodel WHERE locationId = :locationId")
    fun getLocationById(locationId: String): LiveData<WeatherModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: WeatherModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryList: List<WeatherModel>)

    @Update
    suspend fun update(country: WeatherModel)

    @Delete
    suspend fun delete(country: WeatherModel)

    @Query("DELETE FROM weathermodel")
    suspend fun clearTable()

}