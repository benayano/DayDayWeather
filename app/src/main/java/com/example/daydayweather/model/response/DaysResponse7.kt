package com.example.daydayweather.model.response

import com.example.daydayweather.model.response.Daily
import kotlinx.serialization.Serializable

@Serializable
data class DaysResponse7(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)