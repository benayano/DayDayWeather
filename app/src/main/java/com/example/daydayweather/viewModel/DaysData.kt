package com.example.daydayweather.viewModel

data class DaysData(val listDaysData: List<DayData>)

data class DayData(
    val name:String,
    val condition:String,
    val lowDegrees: Double,
    val highDegrees: Double,
    val image:Int
)