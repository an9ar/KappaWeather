package com.an9ar.kappaweather.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.theme.KappaWeatherTheme

@Composable
fun MainNavScreen() {
    KappaWeatherTheme {
        Surface(color = AppTheme.colors.background) {
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

                    }
                    composable(Screens.LocationScreen.routeName) {

                    }
                    composable(Screens.SettingsScreen.routeName) {

                    }
                }
            }
        }
    }
}

@Composable
fun KappaWeatherBottomNavigation(
    navHostController: NavHostController,
    navItems: List<Screens>
) {
    BottomNavigation {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        navItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.screenIcon,
                        tint = AppTheme.colors.text
                    )
                },
                label = { Text(text = screen.screenName) },
                selected = currentRoute == screen.routeName,
                onClick = {
                    navHostController.navigate(screen.routeName) {
                        popUpTo = navHostController.graph.startDestination
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}