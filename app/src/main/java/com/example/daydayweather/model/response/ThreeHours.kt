package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ThreeHours(
    //    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
//    val pop: Int,
//    val sys: SysX,
//    val pod: String,
    val dt_txt: String

)


