package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel,
    onDataReceivingFinish: () -> Unit
) {
    mainViewModel.getCitiesList (
        onFinish = { onDataReceivingFinish() }
    )
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)
    ) {
        Image(
            imageVector = vectorResource(id = R.drawable.ic_kappa_sign),
            colorFilter = ColorFilter.tint(AppTheme.colors.text),
            modifier = Modifier.preferredSize(128.dp)
        )
    }
}