package com.an9ar.kappaweather.screens

import androidx.compose.foundation.background
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
    val locationCities = mainViewModel.locationsList.observeAsState(initial = emptyList())
    if (locationCities.value.isNotEmpty()) {
        WeatherPagerScreen(
            mainViewModel = mainViewModel,
            locations = locationCities.value
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
    locations: List<CityModel>
) {
    pagerState.maxPage = locations.size - 1

    val currentWeatherState = mainViewModel.getlocationWeather().observeAsState(
        initial = Resource(
            status = Resource.Status.COMPLETED,
            data = WeatherModel.EMPTY,
            message = ""
        )
    )

    Pager(
        state = pagerState,
        offscreenLimit = 1,
        onPageOpen = { pageIndex ->
            mainViewModel.setSelectedWeatherLocation(location = locations[pageIndex])
            log("select TAB numero $pageIndex")
        }
    ) {

        WeatherPagerContent(
            currentLocation = locations[page],
            currentWeatherState = currentWeatherState.value
        )
    }
}

@Composable
fun WeatherPagerContent(
    currentLocation: CityModel,
    currentWeatherState: Resource<WeatherModel>
) {
    when (currentWeatherState.status) {
        Resource.Status.LOADING -> {
            log("entering to LOADING")
            WeatherPagerLoadingScreen()
        }
        Resource.Status.SUCCESS -> {
            log("entering to SUCCESS")
            log("SUCCESS data - ${currentWeatherState.data}")
            /*currentWeatherState.data?.let {

            }*/
            if (currentWeatherState.data == null) {
                log("LIST IS NULL")
                WeatherPagerLoadingScreen()
            }
            else {
                log("LIST IS NOT NULL")
                WeatherPagerSuccessScreen(
                    currentLocation = currentLocation,
                    weatherInfo = currentWeatherState.data
                )
            }
        }
        Resource.Status.ERROR -> {}
    }
}

@Composable
fun WeatherPagerSuccessScreen(
    currentLocation: CityModel,
    weatherInfo: WeatherModel
) {
    Column(modifier = Modifier.background(AppTheme.colors.warning)) {
        Text(
            text = currentLocation.name,
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