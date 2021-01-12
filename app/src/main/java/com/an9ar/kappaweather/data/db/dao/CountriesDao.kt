package com.an9ar.kappaweather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countrymodel")
    fun getCountriesList(): LiveData<List<CountryModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: CountryModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryList: List<CountryModel>)

    @Update
    suspend fun update(country: CountryModel)

    @Delete
    suspend fun delete(country: CountryModel)

    @Query("DELETE FROM countrymodel")
    suspend fun clearTable()

}