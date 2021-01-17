package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.an9ar.kappaweather.domain.LocationRepository

class MainViewModel @ViewModelInject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    val splashStatus = locationRepository.getCountriesList()
    val countriesList = locationRepository.updateCountriesList()
    val citiesList = locationRepository.updateCitiesList()

}