package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable

data class ThreeHours(
    val clouds: Clouds,
//    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Int,
    val sys: SysX,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)