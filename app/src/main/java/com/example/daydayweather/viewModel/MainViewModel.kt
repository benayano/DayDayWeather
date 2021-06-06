package com.example.daydayweather.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.db.PlacesEntity
import kotlinx.coroutines.launch
import java.util.*
import androidx.lifecycle.map


class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val currentTime: MutableLiveData<CurrentTimeData> by lazy {
        MutableLiveData()
    }

    private val hours: MutableLiveData<List<ThreeHourData>> by lazy {
        MutableLiveData()
    }

    private val days: MutableLiveData<List<DayData>> by lazy {
        MutableLiveData()
    }

    private val locations = placesRepository
        .getAllPlaces()
        .map { ListLocationsData(converter.entityListToLocationList(it)) }

    private val converter = Converter()

    //--------------------------current Time ----------------------------------
    fun getCurrentWeather() = currentTime

    fun loadCurrentWeather(cityName: String) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCity(cityName)
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.postValue(currentData)
        }
    }

    fun loadCurrentWeather(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.postValue(currentData)
        }
    }

    //-----------------------------------Hours----------------------------
    fun getHours(): LiveData<List<ThreeHourData>> = hours

    fun loadHours(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getHoursByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val listHoursData = converter.hoursResponseToData(currentResponse)
            hours.postValue(listHoursData)
        }
    }

    //------------------------------------------Days--------------------------
    fun getDays(): LiveData<List<DayData>> = days

    fun loadDays(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val daysRepository =
                weatherRepository.daysWeatherByCoordinates(
                    locationLat = latitude,
                    locationLon = longitude
                )
            days.postValue(converter.allDays(daysRepository))
        }
    }

    //--------------------------------places--------------------------------------------
    //
    fun getLocations(): LiveData<ListLocationsData> = locations

    fun onAddingLocation(locationData: LocationData) {
        viewModelScope.launch {
            val placesEntity = PlacesEntity(
                id = 3,
                name = locationData.name,
                country = "this not current!!!", //locationData.country,
                longitude = 0.53333,  // longitude = locationData.longitude,
                latitude = 0.777777  // latitude = locationData.latitude
            )
            placesRepository.addPlace(placesEntity)
        }
    }


}