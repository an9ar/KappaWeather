package com.an9ar.kappaweather

import android.text.format.DateFormat
import android.util.Log

fun log(logMessage: String) = Log.i("LOG_TAG", logMessage)

fun convertDate(dateInMilliseconds: Long): String {
    return DateFormat.format("dd/MM/yyyy hh:mm:ss", dateInMilliseconds).toString()
}