package com.example.daydayweather.model.repository

import android.content.SharedPreferences
import com.example.daydayweather.viewModel.LocationData

class LastCityChose(private val sharedPreferences: SharedPreferences) {

    private val editor = sharedPreferences.edit()

    companion object {
        private const val CITY_NAME = "cityName"
        private const val COUNTRY = "country"
        private const val LONGITUDE = "longitude"
        private const val LATITUDE = "latitude"
    }

    fun saveLocationData(
        name: String,
        country: String,
        longitude: Double,
        latitude: Double
    ) {
        editor.apply {
            putString(CITY_NAME, name)
            putString(COUNTRY, country)
            putFloat(LONGITUDE, longitude.toFloat())
            putFloat(LATITUDE, latitude.toFloat())
            apply()
        }
    }


    fun loadLocationData() = LocationData(
        name = sharedPreferences.getString(CITY_NAME, "ירושלים")!!,
        country = sharedPreferences.getString(COUNTRY, "IL")!!,
        longitude = sharedPreferences.getFloat(LONGITUDE, 31.778242F).toDouble(),
        latitude = sharedPreferences.getFloat(LATITUDE, 35.232764F).toDouble()
    )


}