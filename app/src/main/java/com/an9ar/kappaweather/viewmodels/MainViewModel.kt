package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.domain.LocationRepository
import com.an9ar.kappaweather.network.utils.Resource

class MainViewModel @ViewModelInject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    var citiesList = MutableLiveData<Resource<List<CityModel>>>()

    val splashStatus = locationRepository.getCountriesList()
    val countriesList = locationRepository.updateCountriesList()

    fun getCitiesList(countryId: String) {
        citiesList.value = locationRepository.getCitiesList(countryId = countryId).value
    }

    fun updateCitiesList(countryId: String) {
        citiesList.value = locationRepository.updateCitiesList(countryId = countryId).value
    }
}