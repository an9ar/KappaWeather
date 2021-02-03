package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
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
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    val locationsWeatherlist = mainViewModel.locationsWeatherlist.observeAsState(initial = emptyList())
    log("locationsWeatherlist - ${locationsWeatherlist.value}")

    WeatherScreenContent(
        navHostController = navHostController,
        mainViewModel = mainViewModel,
        locations = locationsWeatherlist.value
    )
}

@Composable
fun WeatherScreenContent(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    locations: List<WeatherModel>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        var currentLocationPageInfo by remember { mutableStateOf(WeatherModel.EMPTY) }
        Image(
            bitmap = imageResource(id = R.drawable.few_clouds_1),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (locations.isNotEmpty()) {
                LocationTitle(
                    weatherInfo = currentLocationPageInfo,
                    navHostController = navHostController,
                    mainViewModel = mainViewModel
                )
                WeatherPagerScreen(
                    locations = locations.sortedBy { it.locationId },
                    onLocationPageOpen = { pageInfo ->
                        currentLocationPageInfo = pageInfo
                    }
                )
            }
            else {
                EmptyLocationTitle(navHostController = navHostController)
                //WeatherEmptyScreen()
            }
        }

    }
}

@Composable
fun WeatherEmptyScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "EMPTY", color = AppTheme.colors.text)
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
    locations: List<WeatherModel>,
    onLocationPageOpen: (WeatherModel) -> Unit
) {
    pagerState.maxPage = locations.size - 1

    Pager(
        state = pagerState,
        offscreenLimit = 1,
        onPageOpen = { pageIndex ->
            //mainViewModel.setSelectedWeatherLocation(location = locations[pageIndex])
            onLocationPageOpen(locations[pageIndex])
            log("select TAB numero $pageIndex")
        }
    ) {
        WeatherPagerContent(
            weatherInfo = locations[page]
        )
    }
}

@Composable
fun WeatherPagerContent(
    weatherInfo: WeatherModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {
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

@Composable
fun EmptyLocationTitle(navHostController: NavHostController) {
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
        val (menuItem, titleBlock) = createRefs()

        IconButton(
            onClick = {
                navHostController.navigate(Screens.MenuScreen.routeName)
            },
            modifier = Modifier.constrainAs(menuItem) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu button",
                tint = AppTheme.colors.uiSurface
            )
        }
        Text(
            text = "Empty list",
            color = AppTheme.colors.text,
            style = AppTheme.typography.h6,
            modifier = Modifier.constrainAs(titleBlock) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun LocationTitle(
    weatherInfo: WeatherModel,
    navHostController: NavHostController,
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
        val (menuItem, titleBlock, refreshButton) = createRefs()

        IconButton(
            onClick = {
                navHostController.navigate(Screens.MenuScreen.routeName)
            },
            modifier = Modifier.constrainAs(menuItem) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu button",
                tint = AppTheme.colors.uiSurface
            )
        }
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
                color = AppTheme.colors.textSecondary,
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
            modifier = Modifier.constrainAs(refreshButton) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh button",
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.overflowCard)
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
                modifier = Modifier.preferredSize(80.dp)
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
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.weight(0.5f)) {
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
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colors.overflowCard)
    ) {
        ConstraintLayout(modifier = Modifier.padding(16.dp)) {
            val (infoIcon, infoBlock) = createRefs()

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppTheme.colors.uiSurface,
                modifier = Modifier.constrainAs(infoIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )
            Column(
                modifier = Modifier.constrainAs(infoBlock) {
                    top.linkTo(parent.top)
                    start.linkTo(infoIcon.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Text(
                    text = value,
                    style = AppTheme.typography.additionalWeatherInfoValue,
                    color = AppTheme.colors.text
                )
                Text(
                    text = title,
                    style = AppTheme.typography.additionalWeatherInfoTitle,
                    color = AppTheme.colors.textSecondary,
                )
            }
        }
    }
}