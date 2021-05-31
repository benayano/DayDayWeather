package com.example.daydayweather.model.service

import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BrowseApi {

    @GET("onecall")
    suspend fun getDays(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts",
        @Query("appid") ApiKey: String,
        @Query("lang") language: String ="he"
    ): DaysResponse7

    //Hours
    // Call 5 day / 3 hour forecast data
    @GET("forecast")
    suspend fun getHoursByCityName(
        @Query("city name") cityName: String,
        @Query("API key") ApiKey: String,
        @Query("lang") language: String ="he"
    ): ThreeHoursResponse

    @GET("forecast")
    suspend fun getHoursByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") ApiKey: String,
        @Query("lang") language: String ="he"
    ): ThreeHoursResponse

    //now time weather
    ///{lat}&{lon}&{API key}
    @GET("weather")
    suspend fun getNowByCoordinates(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("appid") ApiKey: String,
        @Query("lang") language: String ="he"
    ): CurrentTimeResponse

    ///{city name}&{API key}suspend
    @GET("weather")
    suspend fun getCurrentByCityName(
        @Query("q") cityName: String,
        @Query("appid") ApiKey: String,
        @Query("lang") language: String ="he"
    ): CurrentTimeResponse

}