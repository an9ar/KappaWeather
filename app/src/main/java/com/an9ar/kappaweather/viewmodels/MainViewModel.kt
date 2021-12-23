package com.an9ar.kappaweather.viewmodels

import androidx.lifecycle.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val citySearchQuery = MutableLiveData<String>()

    val splashStatus = locationRepository.getCountriesList()
    val countriesList = locationRepository.updateCountriesList()
    val locationsList = locationRepository.getLocationCitiesList()

    fun setCitySearchQuery(query: String) {
        citySearchQuery.value = query
    }

    fun getCitiesListByCountry(countryId: String): LiveData<Resource<List<CityModel>>> {
        return locationRepository.getCitiesListByCountry(countryId = countryId)
    }

    fun getCitiesListBySearch(countryId: String, searchQuery: String): LiveData<Resource<List<CityModel>>> {
        return locationRepository.getCitiesListBySearch(countryId = countryId, searchQuery = searchQuery)
    }

    //Locations

    fun addLocationCity(city: CityModel) {
        locationRepository.addLocationCity(city = city)
    }

    fun clearLocations() {
        locationRepository.clearLocations()
    }

    //Weather

    val locationsWeatherlist = weatherRepository.getLocalLocationsWeather()

    fun getLocationWeather(objectId: Long, objectName: String, latitude: Double, longitude: Double): Resource.Status = runBlocking(Dispatchers.Default) {
        weatherRepository.getLocationWeather(
            objectId = objectId,
            objectName = objectName,
            latitude = latitude,
            longitude = longitude
        ).await()
    }

}