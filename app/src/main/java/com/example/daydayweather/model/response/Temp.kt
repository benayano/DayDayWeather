package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)