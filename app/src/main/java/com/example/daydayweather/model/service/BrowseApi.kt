package com.example.daydayweather.model.service

import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BrowseApi {

    companion object{
        private const val TOKEN = "42cf5356b60bbd5f1ba1e55a0ec4e820"
        private const val APIKEY = "appid"
        private const val CITY_NAME = "q"
        private const val LANGUAGE = "lang"
        private const val LONGITUDE = "lon"
        private const val LATITUDE = "lat"
    }

    @GET("onecall")
    suspend fun getDays(
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double,
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts",
        @Query(APIKEY) ApiKey: String = TOKEN,
        @Query(LANGUAGE) language: String ="he"
    ): DaysResponse7

    //Hours
    // Call 5 day / 3 hour forecast data
    @GET("forecast")
    suspend fun getHoursByCityName(
        @Query(CITY_NAME) cityName: String,
        @Query(APIKEY) ApiKey: String = TOKEN,
        @Query(LANGUAGE) language: String ="he"
    ): ThreeHoursResponse

    @GET("forecast")
    suspend fun getHoursByCoordinates(
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double,
        @Query(APIKEY) ApiKey: String = TOKEN,
        @Query(LANGUAGE) language: String ="he"
    ): ThreeHoursResponse

    //now time weather

    @GET("weather")
    suspend fun getNowByCoordinates(
        @Query(LONGITUDE) lon: Double,
        @Query(LATITUDE) lat: Double,
        @Query(APIKEY) ApiKey: String = TOKEN,
        @Query(LANGUAGE) language: String ="he"
    ): CurrentTimeResponse

    ///{city name}&{API key}suspend
    @GET("weather")
    suspend fun getCurrentByCityName(
        @Query(CITY_NAME) cityName: String,
        @Query(APIKEY) ApiKey: String = TOKEN,
        @Query(LANGUAGE) language: String ="he"
    ): CurrentTimeResponse

}