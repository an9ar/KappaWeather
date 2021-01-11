package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.dto.CityDTO
import com.an9ar.kappaweather.network.dto.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _countriesList: MutableLiveData<List<CountriesListResponse>> = MutableLiveData()
    val countriesList: LiveData<List<CountriesListResponse>>
        get() = _countriesList

    private val _citiesList: MutableLiveData<List<CityDTO>> = MutableLiveData()
    val citiesList: LiveData<List<CityDTO>>
        get() = _citiesList

    fun getCountriesList() = runBlocking {
        val countriesListResponse: Result<List<CountriesListResponse>> = weatherRepository.getCountriesList()
        countriesListResponse.onResult(
            onSuccess = {
                log("LIST OF COUNTRIES - ${it.asSuccess().value}")
                _countriesList.value = it.asSuccess().value
            },
            onFailure = {
                log("LIST OF COUNTRIES ERROR - ${it.asFailure()}")
            }
        )
    }

    fun getCitiesList() = viewModelScope.launch {
        val citiesList: Result<List<CityDTO>> = weatherRepository.getCitiesList()
        citiesList.onResult(
            onSuccess = {
                log("LIST OF CITIES - ${it.asSuccess().value}")
                _citiesList.value = it.asSuccess().value.sortedBy { city -> city.country }.take(5)
            },
            onFailure = {
                log("LIST OF CITIES ERROR - ${it.asFailure()}")
            }
        )
    }
}