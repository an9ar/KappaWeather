package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.AmbientAnimationClock
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.ui.Pager
import com.an9ar.kappaweather.ui.PagerState

@Composable
fun WeatherScreen() {
    val screensBackgrounds = listOf(
        Brush.linearGradient(colors = listOf(AppTheme.colors.warning, AppTheme.colors.error, AppTheme.colors.dark)),
        Brush.linearGradient(colors = listOf(AppTheme.colors.success, AppTheme.colors.dark, AppTheme.colors.warning)),
        Brush.linearGradient(colors = listOf(AppTheme.colors.error, AppTheme.colors.light, AppTheme.colors.success))
    )

    WeatherPagerScreen(colors = screensBackgrounds)
}

@Composable
fun WeatherPagerScreen(
    pagerState: PagerState = run {
        val clock = AmbientAnimationClock.current
        remember(clock) {
            PagerState(clock)
        }
    },
    colors: List<Brush>
) {
    pagerState.maxPage = colors.size - 1

    Pager(
        state = pagerState,
        offscreenLimit = 1,
        onPageOpen = { pageIndex ->

        }
    ) {
        Box(modifier = Modifier.fillMaxSize().background(colors[page])) {

        }
    }
}