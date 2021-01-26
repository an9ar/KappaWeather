package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainViewModel @ViewModelInject constructor(
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

    var selectedWeatherLocation = MutableLiveData<CityModel>()

    val locationsWeatherlist = weatherRepository.getLocalLocationsWeather()

    fun setSelectedWeatherLocation(location: CityModel) {
        selectedWeatherLocation.value = location
    }

    fun getLocationWeather(objectId: String, latitude: Double, longitude: Double): Resource.Status = runBlocking(Dispatchers.IO) {
        weatherRepository.getLocationWeather(
            objectId = objectId,
            latitude = latitude,
            longitude = longitude
        ).await()
    }

}