package com.example.daydayweather.model.repository

import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import com.example.daydayweather.model.service.BrowseApi
import com.example.daydayweather.model.RetrofitCreator

object WeatherRepository {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val TOKEN = "42cf5356b60bbd5f1ba1e55a0ec4e820"

    private val browseApi: BrowseApi by lazy {
        RetrofitCreator.getRetrofit()
    }


    suspend fun daysWeatherByCoordinates(
        locationLat: Double,
        locationLon: Double
    ): DaysResponse7 = browseApi.getDays(lat = locationLat, lon = locationLon, ApiKey = TOKEN)

    suspend fun getHoursByCityName(
        cityName: String
    ): ThreeHoursResponse = browseApi.getHoursByCityName(cityName = cityName, ApiKey = TOKEN)

    suspend fun getHoursByCoordinates(
        locationLat: Double,
        locationLon: Double
    ): ThreeHoursResponse =
        browseApi.getHoursByCoordinates(lat = locationLat, lon = locationLon, ApiKey = TOKEN)

    suspend fun getCurrentWeatherByCity(cityName: String) = browseApi.getCurrentByCityName(
        cityName,
        TOKEN
    )

    suspend fun getCurrentWeatherByCoordinates(
        locationLat: Double,
        locationLon: Double
    ): CurrentTimeResponse =
        browseApi.getNowByCoordinates(lat = locationLat, lon = locationLon, ApiKey = TOKEN)

}







