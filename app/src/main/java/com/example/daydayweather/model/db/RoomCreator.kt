package com.example.daydayweather.model.db

import android.content.Context
import androidx.room.Room

object RoomCreator {

    fun getDbPlaces(context: Context): PlacesDb {
        return Room.databaseBuilder(context, PlacesDb::class.java, " myPlaces")
            .build()
    }

}