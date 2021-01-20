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
            Screens.WeatherScreen,
            Screens.CountryChooseScreen,
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
                //Tab screens
                composable(Screens.WeatherScreen.routeName) {
                    TestScreen(color = AppTheme.colors.background)
                }
                composable(Screens.SettingsScreen.routeName) {
                    SettingsScreen(navHostController = navHostController)
                }
                //Other screens
                composable(Screens.CountryChooseScreen.routeName) {
                    CountryChooseScreen(mainViewModel = mainViewModel, navHostController = navHostController)
                }
                composable("${Screens.CityChooseScreen.routeName}/{countryId}") { backStackEntry ->
                    backStackEntry.arguments?.getString("countryId")?.let { countryId ->
                        CityChooseScreen(mainViewModel = mainViewModel, navHostController = navHostController, countryId = countryId)
                    }
                }
                composable(Screens.CreditsScreen.routeName) {
                    CreditsScreen(navHostController = navHostController)
                }
            }
        }
    }
}