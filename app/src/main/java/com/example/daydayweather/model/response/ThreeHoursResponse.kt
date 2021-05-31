package com.example.daydayweather.model.response

import com.example.daydayweather.model.response.City
import com.example.daydayweather.model.response.ThreeHours
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreeHoursResponse(
    val city: City,
  //  val cnt: Int,
//   val message: Int,
    val cod: String,
    @SerialName("list")
    val listThreeHours: List<ThreeHours>
)