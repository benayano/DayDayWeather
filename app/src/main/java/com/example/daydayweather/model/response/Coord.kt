package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class Coord(
    val lat: Double,
    val lon: Double
)