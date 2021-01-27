package com.an9ar.kappaweather.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.AmbientAnimationClock
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.convertDate
import com.an9ar.kappaweather.data.models.WeatherModel
import com.an9ar.kappaweather.log
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
    ConstraintLayout(
            modifier = Modifier.fillMaxSize().background(AppTheme.colors.background)
    ) {
        val (locationTitle, iconBlock, temperatureBlock) = createRefs()

        WeatherLocationTitle(
                title = weatherInfo.locationName,
                date = convertDate(weatherInfo.time * 1000),
                modifier = Modifier.constrainAs(locationTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        WeatherIconBlock(
                weatherInfo = weatherInfo.weather.first().description.capitalize(),
                modifier = Modifier.constrainAs(iconBlock) {
                    top.linkTo(locationTitle.bottom)
                    bottom.linkTo(temperatureBlock.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        WeatherTemperatureBlock(
                weatherInfo = weatherInfo,
                modifier = Modifier.constrainAs(temperatureBlock) {
                    top.linkTo(iconBlock.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun WeatherLocationTitle(
        title: String,
        date: String,
        modifier: Modifier
) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                    .fillMaxWidth()
                    .padding(AmbientWindowInsets.current.systemBars
                            .toPaddingValues(bottom = false)
                            .add(top = 8.dp)
                            .add(bottom = 8.dp)
                    )
    ) {
        Text(
                text = title,
                color = AppTheme.colors.text,
                style = AppTheme.typography.h6
        )
        Text(
                text = "Last update: $date",
                color = AppTheme.colors.text,
                style = AppTheme.typography.body2
        )
    }
}

@Composable
fun WeatherIconBlock(
        weatherInfo: String,
        modifier: Modifier
) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
    ) {
        Image(
                imageVector = vectorResource(id = R.drawable.ic_kappa_sign),
                colorFilter = ColorFilter.tint(AppTheme.colors.text),
                modifier = Modifier.preferredSize(128.dp)
        )
        Spacer(modifier = Modifier.preferredHeight(16.dp))
        WeatherDescription(description = weatherInfo)
    }
}

@Composable
fun WeatherTemperatureBlock(
        weatherInfo: WeatherModel,
        modifier: Modifier
) {
    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.3f)) {
            WeatherAdditionalTemperatureWidget(temperature = weatherInfo.mainInformation.temp_min.roundToInt())
            WeatherDescription("Min.")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.4f)) {
            WeatherCurrentTemperatureWidget(temperature = weatherInfo.mainInformation.temp.roundToInt())
            WeatherDescription("Feels like ${weatherInfo.mainInformation.feels_like.roundToInt()}°")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.3f)) {
            WeatherAdditionalTemperatureWidget(temperature = weatherInfo.mainInformation.temp_max.roundToInt())
            WeatherDescription("Max.")
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
fun WeatherDescription(description: String) {
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