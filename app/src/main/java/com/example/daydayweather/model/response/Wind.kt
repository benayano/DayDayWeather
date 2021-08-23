package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class Wind(
    val deg: Int = 0,
    //   val gust: Double,
    val speed: Double = 0.0
)