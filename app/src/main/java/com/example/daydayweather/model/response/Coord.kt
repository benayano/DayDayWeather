package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class Coord(
    val lat: Double=31.7760,
    val lon: Double=35.2358
)