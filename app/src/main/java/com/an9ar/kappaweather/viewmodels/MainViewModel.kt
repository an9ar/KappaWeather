package com.an9ar.kappaweather.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an9ar.kappaweather.domain.WeatherRepository
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.CountriesListResponse
import com.an9ar.kappaweather.network.retrofit_result.*
import kotlinx.coroutines.runBlocking

class MainViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _countriesList: MutableLiveData<List<CountriesListResponse>> = MutableLiveData()
    val countriesList: LiveData<List<CountriesListResponse>>
        get() = _countriesList

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
}