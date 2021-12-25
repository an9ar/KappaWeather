package com.an9ar.kappaweather.data.db.dao

import androidx.room.*
import com.an9ar.kappaweather.data.models.CountryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countrymodel")
    fun getCountriesList(): Flow<List<CountryModel>>

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