package com.an9ar.kappaweather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.an9ar.kappaweather.screens.Screens
import com.an9ar.kappaweather.theme.AppTheme

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
            modifier = Modifier.fillMaxWidth().preferredHeight(AppTheme.sizes.bottomNavigationHeight),
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
                icon = { screen.screenIcon?.let { Icon(imageVector = it) } },
                selectedContentColor = AppTheme.colors.bottomNavItem,
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