package com.an9ar.kappaweather.screens

sealed class Screens(val routeName: String) {
    object WeatherScreen : Screens(routeName = "WeatherScreen")
    object MenuScreen : Screens(routeName = "MenuScreen")
    object LocationsScreen : Screens(routeName = "LocationsScreen")
    object CountryChooseScreen : Screens(routeName = "CountryChooseScreen")
    object CityChooseScreen : Screens(routeName = "CityChooseScreen")
    object CitySearchScreen : Screens(routeName = "CitySearchScreen")
    object SettingsScreen : Screens(routeName = "SettingsScreen")
    object CreditsScreen : Screens(routeName = "CreditsScreen")
}