package com.example.daydayweather.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daydayweather.model.repository.LastCityChose
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository


class WeatherFactory(
    private val weatherRepository: WeatherRepository,
    private val placesRepository: PlacesRepository,
    private val lastCityChose: LastCityChose
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepository, placesRepository, lastCityChose) as T
    }
}