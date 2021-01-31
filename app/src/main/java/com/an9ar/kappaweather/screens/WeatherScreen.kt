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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
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
    val locationsWeatherlist =
        mainViewModel.locationsWeatherlist.observeAsState(initial = emptyList())
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        LocationTitle(
            weatherInfo = weatherInfo,
            mainViewModel = mainViewModel
        )
        Column(modifier = Modifier.fillMaxSize()) {
            WeatherTemperatureInfoBlock(
                weatherInfo = weatherInfo,
                modifier = Modifier.weight(0.5f)
            )
            WeatherAdditionalInfoBlock(
                weatherInfo = weatherInfo,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(bottom = AppTheme.sizes.bottomNavigationHeight)
            )
        }
    }
}

@Composable
fun LocationTitle(
    weatherInfo: WeatherModel,
    mainViewModel: MainViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                AmbientWindowInsets.current.systemBars
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
                contentDescription = "Refresh weather",
                tint = AppTheme.colors.uiSurface
            )
        }
    }
}

@Composable
fun WeatherTemperatureInfoBlock(
    weatherInfo: WeatherModel,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            imageVector = vectorResource(id = weatherInfo.weather.first().description.toWeatherType().iconId),
            contentDescription = "Weather icon",
            colorFilter = ColorFilter.tint(AppTheme.colors.text),
            modifier = Modifier.preferredSize(96.dp)
        )
        Text(
            text = "${weatherInfo.mainInformation.temp.roundToInt()}°",
            color = AppTheme.colors.text,
            style = AppTheme.typography.weatherCurrentTemperature
        )
        Text(
            text = weatherInfo.weather.first().description,
            color = AppTheme.colors.text,
            style = AppTheme.typography.h6
        )
    }
}

@Composable
fun WeatherAdditionalInfoBlock(
    weatherInfo: WeatherModel,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.weight(0.5f)) {
            WeatherInfoCard(
                icon = vectorResource(id = R.drawable.ic_info_weather_temp_min),
                title = "Min.",
                value = "${weatherInfo.mainInformation.temp_min.roundToInt()}°",
                modifier = Modifier.weight(0.5f)
            )
            WeatherInfoCard(
                icon = vectorResource(id = R.drawable.ic_info_weather_temp_max),
                title = "Max.",
                value = "${weatherInfo.mainInformation.temp_max.roundToInt()}°",
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(modifier = Modifier.weight(0.5f)) {
            WeatherInfoCard(
                icon = vectorResource(id = R.drawable.ic_info_weather_pressure),
                title = "Pressure",
                value = "${weatherInfo.mainInformation.pressure} mm",
                modifier = Modifier.weight(0.5f)
            )
            WeatherInfoCard(
                icon = vectorResource(id = R.drawable.ic_info_weather_humidity),
                title = "Humidity",
                value = "${weatherInfo.mainInformation.humidity}%",
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

@Composable
fun WeatherInfoCard(
    icon: ImageVector,
    title: String,
    value: String,
    color: Color = Color.Unspecified,
    modifier: Modifier
) {
    ConstraintLayout(modifier = modifier
        .fillMaxHeight()
        .background(color)
        .padding(16.dp)) {
        val (infoIcon, infoTitle, infoValue) = createRefs()

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.constrainAs(infoIcon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 16.dp)
                bottom.linkTo(parent.bottom)
            }
        )
        Column(
            modifier = Modifier.constrainAs(infoTitle) {
                top.linkTo(parent.top)
                start.linkTo(infoIcon.end, 16.dp)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = title,
                style = AppTheme.typography.additionalWeatherInfoTitle,
                color = AppTheme.colors.text,

            )
            Text(
                text = value,
                style = AppTheme.typography.additionalWeatherInfoValue,
                color = AppTheme.colors.textSecondary
            )
        }

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