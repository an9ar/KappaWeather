package com.an9ar.kappaweather.network.retrofit_result

interface HttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}