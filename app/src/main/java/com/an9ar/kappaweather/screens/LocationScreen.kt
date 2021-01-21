package com.an9ar.kappaweather.screens

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun LocationsScreen(
        mainViewModel: MainViewModel,
        navHostController: NavHostController
) {
    Scaffold(
            topBar = {
                TopAppBar(backgroundColor = AppTheme.colors.toolbar) {
                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                        val (screenTitle, backButton) = createRefs()
                        Text(
                                text = "Locations managing",
                                style = AppTheme.typography.h6,
                                color = AppTheme.colors.text,
                                modifier = Modifier.constrainAs(screenTitle) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )
                    }
                }
            }
    ) {
        LocationsScreenContent(navHostController)
    }
}

@Composable
fun LocationsScreenContent(navHostController: NavHostController) {

}
