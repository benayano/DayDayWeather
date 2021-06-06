package com.example.daydayweather.viewModel

data class ListLocationsData(
    val listLocationsData: List<LocationData> = emptyList()
)

data class LocationData(
    val sirialNumber: Int,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double
)
