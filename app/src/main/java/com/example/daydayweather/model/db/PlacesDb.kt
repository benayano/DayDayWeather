package com.example.daydayweather.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlacesEntity::class],
    version = 1
)
abstract class PlacesDb : RoomDatabase() {
    abstract fun getPlaceDao(): PlacesDAO
}