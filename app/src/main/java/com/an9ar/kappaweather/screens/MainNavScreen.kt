package com.an9ar.kappaweather.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.theme.KappaWeatherTheme
import com.an9ar.kappaweather.ui.KappaWeatherBottomNavigation
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun MainNavScreen(
    mainViewModel: MainViewModel
) {
    KappaWeatherTheme {
        Surface(color = AppTheme.colors.success) {
            val navHostController = rememberNavController()
            val navItems = listOf(
                Screens.WeatherScreen,
                Screens.LocationScreen,
                Screens.SettingsScreen
            )
            Scaffold(
                bottomBar = {
                    KappaWeatherBottomNavigation(
                        navHostController = navHostController,
                        navItems = navItems
                    )
                }
            ) {
                NavHost(navController = navHostController, startDestination = Screens.WeatherScreen.routeName) {
                    composable(Screens.WeatherScreen.routeName) {
                        ColorScreen(color = AppTheme.colors.background)
                    }
                    composable(Screens.LocationScreen.routeName) {
                        LocationScreen(mainViewModel = mainViewModel)
                    }
                    composable(Screens.SettingsScreen.routeName) {
                        ColorScreen(color = AppTheme.colors.background)
                    }
                }
            }
        }
    }
}