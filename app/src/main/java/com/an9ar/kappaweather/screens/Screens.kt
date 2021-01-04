package com.an9ar.kappaweather.screens

sealed class Screens(name: String) {
    object WeatherScreen : Screens(name = "WeatherScreen")
    object LocationScreen : Screens(name = "WeatherScreen")
    object SettingsScreen : Screens(name = "WeatherScreen")
}