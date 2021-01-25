package com.an9ar.kappaweather.data.db

import androidx.room.TypeConverter
import com.an9ar.kappaweather.data.models.*

class CoordinatesModelConverter {
    @TypeConverter
    fun fromValue(value: CoordinatesModel?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): CoordinatesModel? = toJson(valueString)
}

class WeatherInformationModelConverter {
    @TypeConverter
    fun fromValue(value: List<WeatherInformationModel>?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): List<WeatherInformationModel>? = toJson(valueString)
}

class MainInformationModelConverter {
    @TypeConverter
    fun fromValue(value: MainInformationModel?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): MainInformationModel? = toJson(valueString)
}

class WindInformationModelConverter {
    @TypeConverter
    fun fromValue(value: WindInformationModel?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): WindInformationModel? = toJson(valueString)
}

class CloudsInformationModelConverter {
    @TypeConverter
    fun fromValue(value: CloudsInformationModel?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): CloudsInformationModel? = toJson(valueString)
}

class LocationInformationModelConverter {
    @TypeConverter
    fun fromValue(value: LocationInformationModel?): String? = fromJson(value)
    @TypeConverter
    fun toValue(valueString: String?): LocationInformationModel? = toJson(valueString)
}