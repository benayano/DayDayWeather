package com.example.daydayweather.viewModel

import androidx.lifecycle.*
import com.example.daydayweather.model.repository.LastCityChose
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.model.response.Coord
import kotlinx.coroutines.launch


class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placesRepository: PlacesRepository,
    private val lastCityChose: LastCityChose
) : ViewModel() {


    private val lastCity = MutableLiveData<LocationData>()

    val currentTime: MutableLiveData<CurrentTimeData> by lazy {
        MutableLiveData()
    }

    private val hours: MutableLiveData<List<ThreeHourData>> by lazy {
        MutableLiveData()
    }

    val days: MutableLiveData<List<DayData>> by lazy {
        MutableLiveData()
    }

    private val locations = placesRepository
        .getAllPlaces()
        .map { converter.entityListToLocationList(it) }

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

    fun loadCurrentWeather(coord: Coord) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = coord.lon,
                locationLon = coord.lat
            )
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.value = currentData
        }
    }

    private fun loadCurrentWeather(locationData: LocationData) {
        val longitude: Double = locationData.longitude
        val latitude: Double = locationData.latitude
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.value = currentData
        }
    }

    //-----------------------------------Hours----------------------------
    fun getHours(): LiveData<List<ThreeHourData>> = hours

    private fun loadHours(locationData:LocationData) {
        val longitude: Double = locationData.longitude
        val latitude: Double = locationData.latitude
        viewModelScope.launch {
            val currentResponse = weatherRepository.getHoursByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val listHoursData = converter.getHoursResponseToData(currentResponse)
            hours.postValue(listHoursData)

        }
    }

    //------------------------------------------Days--------------------------
    fun getDays(): LiveData<List<DayData>> = days

    fun loadDays(locationData: LocationData) {
        val longitude: Double = locationData.longitude
        val latitude: Double = locationData.latitude
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
    fun getLocations(): LiveData<List<LocationData>> = locations

    fun addLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.addPlace(converter.locationDataToPlaceEntity(locationData))
        }

    fun addLocation(locationName: String) =
        viewModelScope.launch {
            val forecast = weatherRepository.getCurrentWeatherByCity(locationName)
            if (forecast.cod == 200) {
                placesRepository.addPlace(
                    converter.locationDataToPlaceEntity(
                        converter.currentResponseToLocationData(
                            forecast
                        )
                    )
                )
            }
        }


    fun replaceLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.addPlaceOrUpdate(converter.locationDataToPlaceEntity(locationData))
        }

    fun deleteLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.deletePlace(converter.locationDataToPlaceEntity(locationData))
        }

    fun updateAllForecast(locationData: LocationData) {

        viewModelScope.launch {
            loadCurrentWeather(locationData)
            loadDays(locationData)
            loadHours(locationData)
        }
    }

    //------------------------------locationData----------------------------------------
    fun getLastCity() = lastCity
    fun loadLastCity() {
        viewModelScope.launch {
            lastCity.postValue(lastCityChose.loadLastCity())
        }
    }

    fun saveLastCity(locationData: LocationData) {
        viewModelScope.launch {
            lastCityChose.saveLastLocationData(locationData)
        }
    }


}