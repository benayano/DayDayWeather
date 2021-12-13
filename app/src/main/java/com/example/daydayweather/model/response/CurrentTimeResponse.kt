package com.example.daydayweather.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CurrentTimeResponse(
    // val id:Int,
    //  val base: String,
    val clouds: Clouds = Clouds(0),
    val code: Int = -1,
    val coord: Coord = Coord(),
    //   val dt: Int,
    val main: Main,
    val name: String = "ירושלים default place",
    val sys: Sys= Sys(),
    //val timezone: Int,
    val visibility: Int = -1,
    val weather: List<Weather> = listOf<Weather>(),
    val wind: Wind=Wind()
)