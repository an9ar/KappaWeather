package com.an9ar.kappaweather.network.dto

import com.an9ar.kappaweather.data.models.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    @SerialName("coord") val coordinates: CoordinatesDTO?,
    @SerialName("weather") val weather: List<WeatherInformationDTO>,
    @SerialName("base") val base: String,
    @SerialName("main") val mainInformation: MainInformationDTO,
    @SerialName("visibility") val visibility: Long,
    @SerialName("wind") val wind: WindInformationDTO,
    @SerialName("clouds") val clouds: CloudsInformationDTO,
    @SerialName("dt") val time: Long,
    @SerialName("sys") val location: LocationInformationDTO,
    @SerialName("timezone") val timezone: Long,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("cod") val code: Int
)

@Serializable
data class CoordinatesDTO(
    @SerialName("lon") val longitude: Double,
    @SerialName("lat") val latitude: Double
)

@Serializable
data class WeatherInformationDTO(
    @SerialName("id") val id: Long,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)

@Serializable
data class MainInformationDTO(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feels_like: Double,
    @SerialName("temp_min") val temp_min: Double,
    @SerialName("temp_max") val temp_max: Double,
    @SerialName("pressure") val pressure: Long,
    @SerialName("humidity") val humidity: Long,
    @SerialName("sea_level") val sea_level: Long,
    @SerialName("grnd_level") val grnd_level: Long
)

@Serializable
data class WindInformationDTO(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Long
)

@Serializable
data class CloudsInformationDTO(
    @SerialName("all") val all: Int
)

@Serializable
data class LocationInformationDTO(
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long
)

fun CurrentWeatherDTO?.toWeatherModel(objectId: String): WeatherModel {
    return this?.let {
        WeatherModel(
            locationId = objectId,
            coordinates = coordinates.toCoordinatesModel() ?: CoordinatesModel.EMPTY,
            weather = weather.map { it.toWeatherInformationModel() },
            base = base,
            mainInformation = mainInformation.toMainInformationModel(),
            visibility = visibility,
            wind = wind.toWindInformationModel(),
            clouds = clouds.toCloudsInformationModel(),
            time = time,
            location = location.toLocationInformationModel(),
            timezone = timezone,
            id = id,
            name = name,
            code = code
        )
    } ?: WeatherModel.EMPTY
}

fun CoordinatesDTO?.toCoordinatesModel(): CoordinatesModel {
    return this?.let{
        CoordinatesModel(
            longitude = longitude,
            latitude = latitude
        )
    } ?: CoordinatesModel.EMPTY
}

fun WeatherInformationDTO?.toWeatherInformationModel(): WeatherInformationModel {
    return this?.let{
        WeatherInformationModel(
            id = id,
            main = main,
            description = description,
            icon = icon
        )
    } ?: WeatherInformationModel.EMPTY
}

fun MainInformationDTO?.toMainInformationModel(): MainInformationModel {
    return this?.let{
        MainInformationModel(
            temp = temp,
            feels_like = feels_like,
            temp_min = temp_min,
            temp_max = temp_max,
            pressure = pressure,
            humidity = humidity,
            sea_level = sea_level,
            grnd_level = grnd_level
        )
    } ?: MainInformationModel.EMPTY
}

fun WindInformationDTO?.toWindInformationModel(): WindInformationModel {
    return this?.let{
        WindInformationModel(
            speed = speed,
            deg = deg
        )
    } ?: WindInformationModel.EMPTY
}

fun CloudsInformationDTO?.toCloudsInformationModel(): CloudsInformationModel {
    return this?.let{
        CloudsInformationModel(
            all = all
        )
    } ?: CloudsInformationModel.EMPTY
}

fun LocationInformationDTO?.toLocationInformationModel(): LocationInformationModel {
    return this?.let{
        LocationInformationModel(
            country = country,
            sunrise = sunrise,
            sunset = sunset
        )
    } ?: LocationInformationModel.EMPTY
}