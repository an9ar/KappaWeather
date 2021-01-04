package com.an9ar.kappaweather.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.theme.KappaWeatherTheme

@Composable
fun MainNavScreen() {
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
                        TestScreen()
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
fun TransparentBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = AppTheme.colors.transparent,
        modifier = modifier
    ) {
        Row(
            Modifier.fillMaxWidth().preferredHeight(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}

@Composable
fun KappaWeatherBottomNavigation(
    navHostController: NavHostController,
    navItems: List<Screens>
) {
    TransparentBottomBar {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        navItems.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.screenIcon) },
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.White,
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