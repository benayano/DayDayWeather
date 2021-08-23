package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class Sys(
    val country: String = "IL",
    //  val id: Int,
    val sunrise: Int = 1,
    val sunset: Int = 1,
    val type: Int = 1
)