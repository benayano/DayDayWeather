package com.example.daydayweather.model.repository

import com.example.daydayweather.model.RetrofitCreator
import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import com.example.daydayweather.model.service.BrowseApi

object WeatherRepository {

    private val browseApi: BrowseApi by lazy {
        RetrofitCreator.getRetrofit("https://api.openweathermap.org/data/2.5/")
    }

    suspend fun daysWeatherByCoordinates(
        locationLat: Double,
        locationLon: Double,
        language:String
    ): DaysResponse7 = browseApi.getDays(lat = locationLat, lon = locationLon, language = language)

    suspend fun getHoursByCoordinates(
        locationLat: Double,
        locationLon: Double,
        language:String

    ): ThreeHoursResponse =
        browseApi.getHoursByCoordinates(lat = locationLat, lon = locationLon, language = language)

    suspend fun getCurrentWeatherByCity(cityName: String) = browseApi.getCurrentByCityName(
        cityName
    )

    suspend fun getCurrentWeatherByCoordinates(
        locationLat: Double,
        locationLon: Double,
        language:String
    ): CurrentTimeResponse =
        browseApi.getNowByCoordinates(lat = locationLat, lon = locationLon, language = language)

}







