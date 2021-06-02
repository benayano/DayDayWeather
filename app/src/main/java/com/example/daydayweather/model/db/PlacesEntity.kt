package com.example.daydayweather.model.db

data class PlacesEntity(
    val name: String? = null,
    val country : String,
    val longitude : Double,
    val latitude : Double
)
