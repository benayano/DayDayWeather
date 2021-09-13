package com.example.daydayweather.viewModel

data class HoursData(val listHoursData: List<ThreeHourData>)

data class ThreeHourData(
    //this is description
    val pressure:Int,
    val humidity:Int,//לחות
    val windSpeed: Double,
    val visibility:Int, //נראות
    val timeAndDate:String,
    val description: String,
    //this is base
    val time: Int,
    val Image: String,
    val degrees: Double,
    val cloudy: Int=99999
)


