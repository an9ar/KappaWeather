package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientAnimationClock
import com.an9ar.kappaweather.data.models.CityModel
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.ui.Pager
import com.an9ar.kappaweather.ui.PagerState
import com.an9ar.kappaweather.viewmodels.MainViewModel

@Composable
fun WeatherScreen(
    mainViewModel: MainViewModel
) {
    val locationsWeatherlist = mainViewModel.locationsWeatherlist.observeAsState(initial = emptyList())
    if (locationsWeatherlist.value.isNotEmpty()) {
        WeatherPagerScreen(
            mainViewModel = mainViewModel,
            locations = locationsWeatherlist.value
        )
    }
}

@Composable
fun WeatherPagerScreen(
    pagerState: PagerState = run {
        val clock = AmbientAnimationClock.current
        remember(clock) {
            PagerState(clock)
        }
    },
    mainViewModel: MainViewModel,
    locations: List<WeatherModel>
) {
    pagerState.maxPage = locations.size - 1

    Pager(
        state = pagerState,
        offscreenLimit = 1,
        onPageOpen = { pageIndex ->
            //mainViewModel.setSelectedWeatherLocation(location = locations[pageIndex])
            log("select TAB numero $pageIndex")
        }
    ) {
        WeatherPagerContent(
            currentLocation = locations[page]
        )
    }
}

@Composable
fun WeatherPagerContent(
    currentLocation: WeatherModel
) {
    WeatherPagerSuccessScreen(
        weatherInfo = currentLocation
    )
}

@Composable
fun WeatherPagerSuccessScreen(
    weatherInfo: WeatherModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = weatherInfo.locationName,
            color = AppTheme.colors.text,
            style = AppTheme.typography.h5
        )
        Text(
            text = weatherInfo.mainInformation.temp.toString(),
            color = AppTheme.colors.text,
            style = AppTheme.typography.h5
        )
    }
}

@Composable
fun WeatherPagerLoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.text
        )
    }
}