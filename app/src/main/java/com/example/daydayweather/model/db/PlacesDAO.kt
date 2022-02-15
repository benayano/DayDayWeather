package com.example.daydayweather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlacesDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(placeEntity: PlacesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(placeEntity: PlacesEntity)

    @Delete
    suspend fun delete(placeEntity: PlacesEntity)

    @Query("SELECT * FROM placesEntity ")
    fun getAllPlaces(): LiveData<List<PlacesEntity>>
}