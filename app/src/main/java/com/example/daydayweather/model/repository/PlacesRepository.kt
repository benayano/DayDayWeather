package com.example.daydayweather.model.repository

import com.example.daydayweather.model.db.PlacesDAO
import com.example.daydayweather.model.db.PlacesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepository(private val placesDAO: PlacesDAO) {

    suspend fun addPlace(placeEntity: PlacesEntity) =
        withContext(Dispatchers.IO) { placesDAO.insert(placeEntity) }

    suspend fun addPlaceOrUpdate(placeEntity: PlacesEntity) =
        withContext(Dispatchers.IO) { placesDAO.insertOrUpdate(placeEntity) }

    suspend fun deletePlace(placeEntity: PlacesEntity) =
        withContext(Dispatchers.IO) { placesDAO.delete(placeEntity) }

    fun getAllPlaces() = placesDAO.getAllPlaces()
}