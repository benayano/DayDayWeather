package com.example.daydayweather.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlacesEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,
    val name: String,
    val country : String,
    val longitude : Double,
    val latitude : Double
)
