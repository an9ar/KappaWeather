package com.an9ar.kappaweather.data.models

import com.an9ar.kappaweather.R

sealed class WeatherType (val iconId: Int) {
    object ClearSky : WeatherType(iconId = R.drawable.ic_weather_clear_sky)
    object FewClouds : WeatherType(iconId = R.drawable.ic_weather_few_clouds)
    object ScatteredClouds : WeatherType(iconId = R.drawable.ic_weather_scattered_clouds)
    object BrokenClouds : WeatherType(iconId = R.drawable.ic_weather_broken_clouds)
    object ShowerRain : WeatherType(iconId = R.drawable.ic_weather_shower_rain)
    object Rain : WeatherType(iconId = R.drawable.ic_weather_rain)
    object Thunderstorm : WeatherType(iconId = R.drawable.ic_weather_thunderstorm)
    object Snow : WeatherType(iconId = R.drawable.ic_weather_snow)
    object Mist : WeatherType(iconId = R.drawable.ic_weather_mist)
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
            else -> WeatherType.ClearSky
        }
    } ?: WeatherType.ClearSky
}