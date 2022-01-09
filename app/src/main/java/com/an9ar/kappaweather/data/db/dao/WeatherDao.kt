package com.an9ar.kappaweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.an9ar.kappaweather.data.models.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weathermodel")
    fun getLocationsList(): Flow<List<WeatherModel>>

    @Query("SELECT * FROM weathermodel WHERE locationId = :locationId")
    fun getLocationById(locationId: String): LiveData<WeatherModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: WeatherModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationsList: List<WeatherModel>)

    @Update
    suspend fun update(location: WeatherModel)

    @Delete
    suspend fun delete(location: WeatherModel)

    @Query("DELETE FROM weathermodel")
    suspend fun clearTable()

}