package com.an9ar.kappaweather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherModel (
    @PrimaryKey
    val locationId: String,
    val locationName: String,
    val coordinates: CoordinatesModel,
    val weather: List<WeatherInformationModel>,
    val base: String,
    val mainInformation: MainInformationModel,
    val visibility: Long,
    val wind: WindInformationModel,
    val clouds: CloudsInformationModel,
    val time: Long,
    val location: LocationInformationModel,
    val timezone: Long,
    val id: Long,
    val name: String,
    val code: Int
) {
    companion object {
        val EMPTY = WeatherModel(
            locationId = "",
            locationName = "",
            coordinates = CoordinatesModel.EMPTY,
            weather = emptyList(),
            base = "",
            mainInformation = MainInformationModel.EMPTY,
            visibility = 0L,
            wind = WindInformationModel.EMPTY,
            clouds = CloudsInformationModel.EMPTY,
            time = 0L,
            location = LocationInformationModel.EMPTY,
            timezone = 0L,
            id = 0L,
            name = "",
            code = 0,
        )
    }
}

data class CoordinatesModel(
    val longitude: Double,
    val latitude: Double
) {
    companion object {
        val EMPTY = CoordinatesModel(
            longitude = 0.0,
            latitude = 0.0
        )
    }
}

data class WeatherInformationModel(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) {
    companion object {
        val EMPTY = WeatherInformationModel(
            id = 0L,
            main = "",
            description = "",
            icon = ""
        )
    }
}

data class MainInformationModel(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val humidity: Long,
    val sea_level: Long,
    val grnd_level: Long
) {
    companion object {
        val EMPTY = MainInformationModel(
            temp = 0.0,
            feels_like = 0.0,
            temp_min = 0.0,
            temp_max = 0.0,
            pressure = 0L,
            humidity = 0L,
            sea_level = 0L,
            grnd_level = 0L
        )
    }
}

data class WindInformationModel(
    val speed: Double,
    val deg: Long
) {
    companion object {
        val EMPTY = WindInformationModel(
            speed = 0.0,
            deg = 0L
        )
    }
}

data class CloudsInformationModel(
    val all: Int
) {
    companion object {
        val EMPTY = CloudsInformationModel(
            all = 0
        )
    }
}

data class LocationInformationModel(
    val country: String,
    val sunrise: Long,
    val sunset: Long
) {
    companion object {
        val EMPTY = LocationInformationModel(
            country = "",
            sunrise = 0L,
            sunset = 0L
        )
    }
}