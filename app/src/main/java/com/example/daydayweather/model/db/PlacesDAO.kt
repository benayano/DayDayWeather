package com.example.daydayweather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlacesDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(placeEntity: PlacesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(placeEntity: PlacesEntity)

    @Delete
    fun delete(placeEntity: PlacesEntity)

    @Query("SELECT * FROM placesEntity ")
    fun getAllPlaces(): LiveData<List<PlacesEntity>>
}