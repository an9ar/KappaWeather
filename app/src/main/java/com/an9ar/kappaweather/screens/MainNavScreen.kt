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
    Surface(color = AppTheme.colors.success) {
        val navHostController = rememberNavController()
        val navItems = listOf(
            Screens.WeatherContainer,
            Screens.LocationsContainer,
            Screens.SettingsContainer
        )
        Scaffold(
            bottomBar = {
                KappaWeatherBottomNavigation(
                    navHostController = navHostController,
                    navItems = navItems
                )
            }
        ) {
            NavHost(navController = navHostController, startDestination = Screens.WeatherContainer.routeName) {

                navigation(startDestination = Screens.WeatherScreen.routeName, route = Screens.WeatherContainer.routeName) {
                    composable(Screens.WeatherScreen.routeName) {
                        TestScreen(color = AppTheme.colors.background)
                    }
                }

                navigation(startDestination = Screens.LocationsScreen.routeName, route = Screens.LocationsContainer.routeName) {
                    composable(Screens.LocationsScreen.routeName) {
                        LocationsScreen(mainViewModel = mainViewModel, navHostController = navHostController)
                    }
                    composable(Screens.CountryChooseScreen.routeName) {
                        CountryChooseScreen(mainViewModel = mainViewModel, navHostController = navHostController)
                    }
                    composable("${Screens.CityChooseScreen.routeName}/{countryId}") { backStackEntry ->
                        backStackEntry.arguments?.getString("countryId")?.let { countryId ->
                            CityChooseScreen(mainViewModel = mainViewModel, navHostController = navHostController, countryId = countryId)
                        }
                    }
                    composable("${Screens.CitySearchScreen.routeName}/{countryId}") { backStackEntry ->
                        backStackEntry.arguments?.getString("countryId")?.let { countryId ->
                            CitySearchScreen(mainViewModel = mainViewModel, navHostController = navHostController, countryId = countryId)
                        }
                    }
                }

                navigation(startDestination = Screens.SettingsScreen.routeName, route = Screens.SettingsContainer.routeName) {
                    composable(Screens.SettingsScreen.routeName) {
                        SettingsScreen(navHostController = navHostController, mainViewModel = mainViewModel)
                    }
                    composable(Screens.CreditsScreen.routeName) {
                        CreditsScreen(navHostController = navHostController)
                    }
                }

            }
        }
    }
}