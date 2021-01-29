package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.convertDate
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.data.models.toWeatherType
import com.an9ar.kappaweather.log
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.ui.Pager
import com.an9ar.kappaweather.ui.PagerState
import com.an9ar.kappaweather.viewmodels.MainViewModel
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.add
import dev.chrisbanes.accompanist.insets.toPaddingValues
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    mainViewModel: MainViewModel
) {
    val locationsWeatherlist = mainViewModel.locationsWeatherlist.observeAsState(initial = emptyList())
    log("locationsWeatherlist - ${locationsWeatherlist.value}")
    if (locationsWeatherlist.value.isNotEmpty()) {
        WeatherPagerScreen(
            mainViewModel = mainViewModel,
            locations = locationsWeatherlist.value.sortedBy { it.locationId }
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
            currentLocation = locations[page],
            mainViewModel = mainViewModel
        )
    }
}

@Composable
fun WeatherPagerContent(
    currentLocation: WeatherModel,
    mainViewModel: MainViewModel
) {
    WeatherPagerSuccessScreen(
        weatherInfo = currentLocation,
        mainViewModel = mainViewModel
    )
}

@Composable
fun WeatherPagerSuccessScreen(
    weatherInfo: WeatherModel,
    mainViewModel: MainViewModel
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)
    ) {
        val (locationTitle, weatherInfoBlock, temperatureBlock) = createRefs()

        LocationTitle(
            weatherInfo = weatherInfo,
            mainViewModel = mainViewModel,
            modifier = Modifier.constrainAs(locationTitle) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Column(modifier = Modifier.constrainAs(weatherInfoBlock) {
            top.linkTo(locationTitle.bottom)
            bottom.linkTo(parent.bottom, 56.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        ) {
            WeatherIconBlock(weatherInfo = weatherInfo)
            Spacer(modifier = Modifier.preferredHeight(64.dp))
            WeatherTemperatureBlock(weatherInfo = weatherInfo)
        }
    }
}

@Composable
fun LocationTitle(
    weatherInfo: WeatherModel,
    mainViewModel: MainViewModel,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(AmbientWindowInsets.current.systemBars
                .toPaddingValues(bottom = false)
                .add(top = 8.dp)
                .add(bottom = 8.dp)
            )
    ) {
        val (titleBlock, refreshButton) = createRefs()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(titleBlock) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = weatherInfo.locationName,
                color = AppTheme.colors.text,
                style = AppTheme.typography.h6
            )
            Text(
                text = "Last update: ${convertDate(weatherInfo.time * 1000)}",
                color = AppTheme.colors.text,
                style = AppTheme.typography.body2
            )
        }
        IconButton(
            onClick = {
                val weatherUpdateState = mainViewModel.getLocationWeather(
                    objectId = weatherInfo.locationId,
                    objectName = weatherInfo.locationName,
                    latitude = weatherInfo.coordinates.latitude,
                    longitude = weatherInfo.coordinates.longitude
                )
                when (weatherUpdateState) {
                    Resource.Status.SUCCESS -> {
                        log("REFRESH SUCCESS")
                    }
                    Resource.Status.ERROR -> {
                        log("REFRESH ERROR")
                    }
                }
            },
            modifier = Modifier
                .constrainAs(refreshButton) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 8.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                tint = AppTheme.colors.uiSurface
            )
        }
    }

}

@Composable
fun WeatherIconBlock(
    weatherInfo: WeatherModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            imageVector = vectorResource(id = weatherInfo.weather.first().description.toWeatherType().iconId),
            colorFilter = ColorFilter.tint(AppTheme.colors.text),
            modifier = Modifier.preferredSize(192.dp)
        )
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        WeatherDescription(description = weatherInfo.weather.first().description)
    }
}

@Composable
fun WeatherTemperatureBlock(
    weatherInfo: WeatherModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.3f)) {
            WeatherAdditionalTemperatureWidget(temperature = weatherInfo.mainInformation.temp_min.roundToInt())
            WeatherDescription(description = "Min.")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.4f)) {
            WeatherCurrentTemperatureWidget(temperature = weatherInfo.mainInformation.temp.roundToInt())
            WeatherDescription(
                description = "Feels like ${weatherInfo.mainInformation.feels_like.roundToInt()}°")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.3f)) {
            WeatherAdditionalTemperatureWidget(temperature = weatherInfo.mainInformation.temp_max.roundToInt())
            WeatherDescription(description = "Max.")
        }
    }
}

@Composable
fun WeatherCurrentTemperatureWidget(temperature: Int) {
    ConstraintLayout {
        val (tempValue, tempSign) = createRefs()

        Text(
            text = "$temperature°",
            color = AppTheme.colors.text,
            style = AppTheme.typography.weatherCurrentTemperature,
            modifier = Modifier.constrainAs(tempValue) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

    }
}

@Composable
fun WeatherAdditionalTemperatureWidget(temperature: Int) {
    ConstraintLayout {
        val (tempValue, tempSign) = createRefs()

        Text(
            text = "$temperature°",
            color = AppTheme.colors.text,
            style = AppTheme.typography.weatherAdditionalTemperature,
            modifier = Modifier.constrainAs(tempValue) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun WeatherDescription(
    description: String
) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(
            text = description,
            color = AppTheme.colors.text,
            style = AppTheme.typography.h6
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