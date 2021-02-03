package com.an9ar.kappaweather.screens

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun MainNavScreen(
    mainViewModel: MainViewModel
) {
    Surface(color = AppTheme.colors.background) {
        val navHostController = rememberNavController()

        NavHost(navController = navHostController, startDestination = Screens.WeatherScreen.routeName) {
            // Main weather screen
            composable(Screens.WeatherScreen.routeName) {
                WeatherScreen(navHostController = navHostController, mainViewModel = mainViewModel)
            }
            // Menu screen
            composable(Screens.MenuScreen.routeName) {
                MenuScreen(navHostController = navHostController)
            }
            // Submenu screens
            composable(Screens.LocationsScreen.routeName) {
                LocationsScreen(mainViewModel = mainViewModel, navHostController = navHostController)
            }
            composable(Screens.CountryChooseScreen.routeName) {
                CountryChooseScreen(
                    mainViewModel = mainViewModel,
                    navHostController = navHostController
                )
            }
            composable("${Screens.CityChooseScreen.routeName}/{countryId}") { backStackEntry ->
                backStackEntry.arguments?.getString("countryId")?.let { countryId ->
                    CityChooseScreen(
                        mainViewModel = mainViewModel,
                        navHostController = navHostController,
                        countryId = countryId
                    )
                }
            }
            composable("${Screens.CitySearchScreen.routeName}/{countryId}") { backStackEntry ->
                backStackEntry.arguments?.getString("countryId")?.let { countryId ->
                    CitySearchScreen(
                        mainViewModel = mainViewModel,
                        navHostController = navHostController,
                        countryId = countryId
                    )
                }
            }

            composable(Screens.CreditsScreen.routeName) {
                CreditsScreen(navHostController = navHostController)
            }
        }
    }
}