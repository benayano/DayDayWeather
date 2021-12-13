package com.example.daydayweather.viewModel

data class ListLocationsData(
    val listLocationsData: List<LocationData> = emptyList()
)

data class LocationData(
    val serialNumber: Int? = null,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double
)
