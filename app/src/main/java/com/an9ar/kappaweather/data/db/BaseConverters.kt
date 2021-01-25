package com.an9ar.kappaweather.data.db

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

val gson = GsonBuilder().create()

inline fun <reified T> fromJson(value: T?): String? {
    if (value == null) {
        return null
    }
    val type = object : TypeToken<T>() {

    }.type
    return gson.toJson(value, type)
}

inline fun <reified T> toJson(valueString: String?): T? {
    if (valueString == null) {
        return null
    }
    val type = object : TypeToken<T>() {

    }.type
    return gson.fromJson(valueString, type)
}