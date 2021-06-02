package com.example.daydayweather.model.repository

import com.example.daydayweather.model.db.PlacesDAO
import com.example.daydayweather.model.db.PlacesEntity

class PlacesRepository(private val placesDAO: PlacesDAO) {

    fun addPlace(placeEntity: PlacesEntity) = placesDAO.insert(placeEntity)

    fun addPlaceOrUpdate(placeEntity: PlacesEntity) = placesDAO.insertOrUpdate(placeEntity)

    fun deletePlace(placeEntity: PlacesEntity) = placesDAO.delete(placeEntity)

    fun getAllPlaces() = placesDAO.getAllPlaces()
}