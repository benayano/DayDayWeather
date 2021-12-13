package com.example.daydayweather.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitCreator {

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }


    inline fun <reified T> getRetrofit(baseUrl: String): T {

        val client = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl(baseUrl)
            .client(client)
            .build()

        return retrofit.create(T::class.java)
    }
}
