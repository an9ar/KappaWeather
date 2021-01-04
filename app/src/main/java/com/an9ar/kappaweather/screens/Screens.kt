package com.an9ar.kappaweather.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val routeName: String, val screenName: String, val screenIcon: ImageVector) {
    object WeatherScreen : Screens(routeName = "WeatherScreen", "Weather", Icons.Outlined.Refresh)
    object LocationScreen : Screens(routeName = "LocationScreen", "Location", Icons.Outlined.LocationOn)
    object SettingsScreen : Screens(routeName = "SettingsScreen", "Settings", Icons.Outlined.Settings)
}