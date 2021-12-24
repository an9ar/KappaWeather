package com.an9ar.kappaweather.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.convertDate
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.data.models.toWeatherType
import com.an9ar.kappaweather.getDateHours
import com.an9ar.kappaweather.network.utils.Resource
import com.an9ar.kappaweather.theme.AppTheme
import com.an9ar.kappaweather.theme.WeatherColors
import com.an9ar.kappaweather.ui.Pager
import com.an9ar.kappaweather.ui.PagerState
import com.an9ar.kappaweather.viewmodels.MainViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    val locationsWeatherList = mainViewModel.locationsWeatherlist.observeAsState(
        initial = Resource(
            status = Resource.Status.LOADING,
            data = emptyList(),
            message = ""
        )
    )

    when (locationsWeatherList.value.status) {
        Resource.Status.LOADING -> {
            WeatherScreenContentLoading()
        }
        Resource.Status.SUCCESS -> {
            locationsWeatherList.value.data?.let {
                WeatherScreenContentSuccess(
                    navHostController = navHostController,
                    mainViewModel = mainViewModel,
                    locations = it
                )
            }
        }
    }
}

@Composable
fun WeatherScreenContentSuccess(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    locations: List<WeatherModel>
) {
    var selectedPageIndex by remember { mutableStateOf(0) }
    var selectedPageColor by remember { mutableStateOf(WeatherColors.white) }
    Scaffold(
        bottomBar = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(AppTheme.sizes.bottomNavigationHeight)
                    .fillMaxWidth()
            ) {
                Crossfade(targetState = selectedPageIndex) { page ->
                    PageIndicator(
                        pagesCount = locations.size,
                        currentPage = page
                    )
                }
            }
        }
    ) {
        val pagerState = remember { PagerState() }
        Box(modifier = Modifier.fillMaxSize()) {
            Crossfade(targetState = selectedPageColor) {
                Box(Modifier.fillMaxSize().background(it)) {

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (locations.isNotEmpty()) {
                    Crossfade(targetState = locations[selectedPageIndex]) {
                        LocationTitle(
                            weatherInfo = it,
                            navHostController = navHostController,
                            mainViewModel = mainViewModel
                        )
                    }
                    WeatherPagerScreen(
                        pagerState = pagerState,
                        locations = locations.sortedBy { it.locationId },
                        onLocationPageOpen = { pageIndex ->
                            selectedPageIndex = pageIndex
                            selectedPageColor = if (getDateHours(locations[pageIndex].time * 1000) < 17) {
                                locations[pageIndex].weather.first().description.toWeatherType().dayColor
                            } else {
                                locations[pageIndex].weather.first().description.toWeatherType().nightColor
                            }
                        }
                    )
                }
                else {
                    EmptyLocationTitle(navHostController = navHostController)
                }
            }
        }
    }
}

@Composable
fun PageIndicator(
    pagesCount: Int,
    currentPage: Int
) {
    Row {
        repeat(pagesCount) {
            if (it == currentPage)
                PageIndicatorDot(isActive = true)
            else
                PageIndicatorDot(isActive = false)
        }
    }
}

@Composable
fun PageIndicatorDot(isActive: Boolean) {
    Canvas(modifier = Modifier.size(32.dp)) {
        drawCircle(
            color = if (isActive) Color.Black else Color.Gray,
            radius = if (isActive) size.width / 6 else size.width / 8,
            center = Offset(
                x = size.width / 2,
                y = size.height / 2
            )
        )
    }
}

@Composable
fun WeatherScreenContentLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.text
        )
    }
}

@Composable
fun WeatherPagerScreen(
    pagerState: PagerState = remember { PagerState() },
    locations: List<WeatherModel>,
    onLocationPageOpen: (Int) -> Unit
) {
    pagerState.maxPage = locations.size - 1

    Pager(
        state = pagerState,
        offscreenLimit = 1,
        onPageOpen = { pageIndex ->
            onLocationPageOpen(pageIndex)
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
                rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyBottom = false,
                    additionalTop = 8.dp,
                    additionalBottom = 8.dp
                )
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
                rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyBottom = false,
                    additionalTop = 8.dp,
                    additionalBottom = 8.dp
                )
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
                mainViewModel.getLocationWeather(
                    objectId = weatherInfo.locationId,
                    objectName = weatherInfo.locationName,
                    latitude = weatherInfo.coordinates.latitude,
                    longitude = weatherInfo.coordinates.longitude
                )
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
                painter = painterResource(id = weatherInfo.weather.first().description.toWeatherType().iconId),
                contentDescription = "Weather icon",
                colorFilter = ColorFilter.tint(AppTheme.colors.text),
                modifier = Modifier.size(80.dp)
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
                icon = painterResource(id = R.drawable.ic_info_weather_temp_min),
                title = "Min.",
                value = "${weatherInfo.mainInformation.temp_min.roundToInt()}°",
                modifier = Modifier.weight(0.5f)
            )
            WeatherInfoCard(
                icon = painterResource(id = R.drawable.ic_info_weather_temp_max),
                title = "Max.",
                value = "${weatherInfo.mainInformation.temp_max.roundToInt()}°",
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.weight(0.5f)) {
            WeatherInfoCard(
                icon = painterResource(id = R.drawable.ic_info_weather_pressure),
                title = "Pressure",
                value = "${weatherInfo.mainInformation.pressure} mm",
                modifier = Modifier.weight(0.5f)
            )
            WeatherInfoCard(
                icon = painterResource(id = R.drawable.ic_info_weather_humidity),
                title = "Humidity",
                value = "${weatherInfo.mainInformation.humidity}%",
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

@Composable
fun WeatherInfoCard(
    icon: Painter,
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
                painter = icon,
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