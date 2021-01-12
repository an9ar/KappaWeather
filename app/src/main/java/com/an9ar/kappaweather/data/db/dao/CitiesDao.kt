package com.an9ar.kappaweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.an9ar.kappaweather.data.models.CityModel

@Dao
interface CitiesDao {

    @Query("SELECT * FROM citymodel")
    fun getCitiesList(): LiveData<List<CityModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cityList: List<CityModel>)

    @Update
    suspend fun update(city: CityModel)

    @Delete
    suspend fun delete(city: CityModel)

    @Query("DELETE FROM citymodel")
    suspend fun clearTable()

}