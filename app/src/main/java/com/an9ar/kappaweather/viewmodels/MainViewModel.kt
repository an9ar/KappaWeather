package com.an9ar.kappaweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.CountryModel
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val countriesList = MutableLiveData<List<CountryModel>>()
    val locationsList = MutableLiveData<List<CityModel>>()
    val locationsWeatherlist = MutableLiveData<List<WeatherModel>>()
    val splashStatus = MutableLiveData<SplashStatus>()
    val citiesList = MutableLiveData<List<CityModel>>()

    init {
        viewModelScope.launch {
            launch {
                locationRepository.getCountriesList().collect {
                    countriesList.value = it
                }
            }
            launch {
                locationRepository.getLocationCitiesList().collect {
                    locationsList.value = it
                }
            }
            launch {
                weatherRepository.getLocalLocationsWeather().collect {
                    locationsWeatherlist.value = it
                }
            }
        }
    }

    fun fetchCountriesList() {
        viewModelScope.launch {
            changeSplashStatus(SplashStatus.LOADING)
            locationRepository.fetchCountriesList()
            changeSplashStatus(SplashStatus.FINISHED)
        }
    }

    private fun changeSplashStatus(status: SplashStatus) {
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

    fun getCitiesListBySearch(countryId: String, searchQuery: String) {
        viewModelScope.launch {
            val result = locationRepository.getCitiesListBySearch(countryId = countryId, searchQuery = searchQuery)
            citiesList.value = result
        }
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