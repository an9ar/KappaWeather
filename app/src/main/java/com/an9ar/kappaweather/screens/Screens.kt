package com.an9ar.kappaweather.screens

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource
import com.an9ar.kappaweather.R

sealed class Screens(
        val routeName: String,
        val screenName: String = "",
        val screenIcon: @Composable () -> Unit = {}
) {
    //Containers
    object WeatherContainer : Screens(routeName = "WeatherContainer", "Weather", { Icon(imageVector = vectorResource(id = R.drawable.ic_weather)) })
    object LocationsContainer : Screens(routeName = "LocationsContainer", "Locations", { Icon(imageVector = vectorResource(id = R.drawable.ic_locations)) })
    object SettingsContainer : Screens(routeName = "SettingsContainer", "Settings", { Icon(imageVector = vectorResource(id = R.drawable.ic_settings)) })

    //Weather Tab screens
    object WeatherScreen : Screens(routeName = "WeatherScreen")

    //Locations Tab screens
    object LocationsScreen : Screens(routeName = "LocationsScreen")
    object CountryChooseScreen : Screens(routeName = "CountryChooseScreen")
    object CityChooseScreen : Screens(routeName = "CityChooseScreen")
    object CitySearchScreen : Screens(routeName = "CitySearchScreen")

    //Settings Tab screens
    object SettingsScreen : Screens(routeName = "SettingsScreen")
    object CreditsScreen : Screens(routeName = "CreditsScreen")
}