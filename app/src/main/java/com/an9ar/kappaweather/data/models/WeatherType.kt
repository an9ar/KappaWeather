package com.an9ar.kappaweather.data.models

import androidx.compose.ui.graphics.Brush
import com.an9ar.kappaweather.R
import com.an9ar.kappaweather.theme.WeatherColors

sealed class WeatherType (val iconId: Int, val dayColor: Brush = WeatherColors.white, val nightColor: Brush = dayColor) {
    object ClearSky : WeatherType(iconId = R.drawable.ic_weather_clear_sky, dayColor = WeatherColors.clearSkyDay, nightColor = WeatherColors.clearSkyNight)
    object FewClouds : WeatherType(iconId = R.drawable.ic_weather_few_clouds, dayColor = WeatherColors.fewCloudsDay)
    object ScatteredClouds : WeatherType(iconId = R.drawable.ic_weather_scattered_clouds)
    object BrokenClouds : WeatherType(iconId = R.drawable.ic_weather_broken_clouds)
    object ShowerRain : WeatherType(iconId = R.drawable.ic_weather_shower_rain)
    object Rain : WeatherType(iconId = R.drawable.ic_weather_rain)
    object Thunderstorm : WeatherType(iconId = R.drawable.ic_weather_thunderstorm)
    object Snow : WeatherType(iconId = R.drawable.ic_weather_snow, dayColor = WeatherColors.snowDay, nightColor = WeatherColors.snowNight)
    object Mist : WeatherType(iconId = R.drawable.ic_weather_mist, dayColor = WeatherColors.mistDay)
    object Undefined : WeatherType(iconId = R.drawable.ic_weather_clear_sky, dayColor = WeatherColors.white)
}

fun String?.toWeatherType(): WeatherType {
    return this?.let{ typeString ->
        return when(typeString) {
            "clear sky" -> WeatherType.ClearSky
            "few clouds" -> WeatherType.FewClouds
            "scattered clouds" -> WeatherType.ScatteredClouds
            "broken clouds" -> WeatherType.BrokenClouds
            "shower rain" -> WeatherType.ShowerRain
            "rain" -> WeatherType.Rain
            "thunderstorm" -> WeatherType.Thunderstorm
            "snow" -> WeatherType.Snow
            "mist" -> WeatherType.Mist
            else -> WeatherType.Undefined
        }
    } ?: WeatherType.ClearSky
}