package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class Wind(
    val deg: Int,
 //   val gust: Double,
    val speed: Double
)