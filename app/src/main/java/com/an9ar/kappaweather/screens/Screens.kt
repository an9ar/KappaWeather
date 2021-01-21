package com.an9ar.kappaweather.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorGroup
import androidx.compose.ui.unit.dp

sealed class Screens(val routeName: String, val screenName: String = "", val screenIcon: ImageVector? = null) {

    //Containers
    object WeatherContainer : Screens(routeName = "WeatherContainer", "Weather", Icons.Outlined.Refresh)
    object LocationsContainer : Screens(routeName = "LocationsContainer", "Locations", Icons.Outlined.LocationOn)
    object SettingsContainer : Screens(routeName = "SettingsContainer", "Settings", Icons.Outlined.Settings)

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