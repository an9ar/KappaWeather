package com.an9ar.kappaweather.viewmodels

import androidx.lifecycle.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val citySearchQuery = MutableLiveData<String>()

    val splashStatus = MutableLiveData<SplashStatus>()
    val countriesList = MutableLiveData<List<CountryModel>>()
    val citiesList = MutableLiveData<List<CityModel>>()

    val locationsList = locationRepository.getLocationCitiesList()

    fun getCountriesList() {
        viewModelScope.launch {
            changeSplashStatus(SplashStatus.LOADING)
            locationRepository.fetchCountriesList()
            val result = locationRepository.getCountriesList()
            changeSplashStatus(SplashStatus.FINISHED)
            countriesList.value = result
        }
    }

    private fun changeSplashStatus(status: SplashStatus) {
        log("- change to $status")
        splashStatus.value = status
    }

    fun getCitiesList(countryId: String) {
        viewModelScope.launch {
            val result = locationRepository.getCitiesListByCountry(countryId = countryId)
            citiesList.value = result
        }
    }

    fun clearCitiesList() {
        citiesList.value = emptyList()
    }

    fun setCitySearchQuery(query: String) {
        citySearchQuery.value = query
    }

    fun getCitiesListBySearch(countryId: String, searchQuery: String): LiveData<Resource<List<CityModel>>> {
        return locationRepository.getCitiesListBySearch(countryId = countryId, searchQuery = searchQuery)
    }

    //Locations

    fun addLocationCityAndFetchWeather(city: CityModel) {
        viewModelScope.launch {
            locationRepository.addLocationCity(city = city)
            weatherRepository.fetchSelectedLocationWeather(
                objectId = System.currentTimeMillis(),
                objectName = city.name,
                latitude = city.lat,
                longitude = city.lng
            )
        }
    }

    fun clearLocations() {
        locationRepository.clearLocations()
    }

    //Weather

    val locationsWeatherlist = weatherRepository.getLocalLocationsWeather()

    fun getLocationWeather(objectId: Long, objectName: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherRepository.fetchSelectedLocationWeather(
                objectId = objectId,
                objectName = objectName,
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    enum class SplashStatus{
        LOADING, FINISHED
    }

}