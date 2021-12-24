package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel,
    onDataReceivingFinish: () -> Unit
) {
    val splashStatus by mainViewModel.splashStatus.observeAsState()
    when (splashStatus) {
        MainViewModel.SplashStatus.LOADING -> {
            log("SPLASH - LOADING")
        }
        MainViewModel.SplashStatus.FINISHED -> {
            onDataReceivingFinish.invoke()
        }
        else -> {
            mainViewModel.getCountriesList()
        }
    }
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_kappa_sign),
            contentDescription = "App logo",
            colorFilter = ColorFilter.tint(AppTheme.colors.text),
            modifier = Modifier.size(128.dp)
        )
    }
}