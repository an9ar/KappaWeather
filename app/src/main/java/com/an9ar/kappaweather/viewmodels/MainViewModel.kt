package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.network.utils.Resource

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

    private var selectedWeatherLocation: CityModel = CityModel.EMPTY

    fun setSelectedWeatherLocation(location: CityModel) {
        selectedWeatherLocation = location
    }

    fun getlocationWeather(): LiveData<Resource<WeatherModel>> {
        return weatherRepository.getlocationWeather(
            objectId = selectedWeatherLocation.objectId,
            latitude = selectedWeatherLocation.lat,
            longitude = selectedWeatherLocation.lng
        )
    }

}