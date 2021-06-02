package com.example.daydayweather.model.db

import androidx.room.*

@Dao
interface PlacesDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(placeEntity: PlacesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(placeEntity: PlacesEntity)

    @Delete
    fun delete(placeEntity: PlacesEntity)

    @Query("SELECT * FROM PlacesEntity")
    fun getAllPlaces(): List<PlacesEntity>
}