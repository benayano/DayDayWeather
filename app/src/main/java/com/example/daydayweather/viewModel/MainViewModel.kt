package com.example.daydayweather.viewModel

import androidx.lifecycle.*
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.model.response.Coord
import kotlinx.coroutines.launch


class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placesRepository: PlacesRepository
) : ViewModel() {

    val myCoord=Coord()

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
    //-----------------------------Coord------------------------------------
//    fun loadCoord(longitude: Double, latitude: Double){
//        val coord=Coord(longitude,latitude)
//        myCoord.value=coord
//    }
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
            currentTime.value=currentData
        }
    }

     fun loadCurrentWeather(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.value=currentData
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
            val listHoursData = converter.getHoursResponseToData(currentResponse)
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
    fun getLocations(): LiveData<List<LocationData>> = locations

    fun addLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.addPlace(converter.locationDataToPlaceEntity(locationData))
        }

    fun addLocation(locationName: String) =
        viewModelScope.launch {
            val forecast = weatherRepository.getCurrentWeatherByCity(locationName)
            if (forecast.cod == 200) {
                placesRepository.addPlace(converter.locationDataToPlaceEntity(converter.currentResponseToLocationData(forecast)))
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

    fun updateAllForecast(longitude:Double,latitude:Double){
        viewModelScope.launch {
            loadCurrentWeather(longitude = longitude,latitude = latitude)
            loadDays(longitude = longitude,latitude = latitude)
            loadHours(longitude = longitude,latitude = latitude)
        }
    }
   // fun saveLastLocation(locationData: LocationData)=


}