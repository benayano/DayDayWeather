package com.example.daydayweather.viewModel

import androidx.lifecycle.*
import com.example.daydayweather.model.repository.LastCityChose
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import kotlinx.coroutines.launch


class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val placesRepository: PlacesRepository,
    private val lastCityChose: LastCityChose,
    private val language: String,
    private val dayOfTheWeek: Array<String> = arrayOf<String>("1", "2", "3", "4", "5", "6", "7")

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

    private val locationsLiveData = placesRepository
        .getAllPlaces()
        .map { converter.entityListToLocationList(it) }

    private val appTitle: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    private val converter = Converter()

    //--------------------------current Time ----------------------------------

    private fun loadCurrentWeather(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude,
                language = language
            )
            val currentData = converter.currentResponseToData(currentResponse)
            currentTime.postValue(currentData)
        }
    }

    //-----------------------------------Hours----------------------------
    fun getHours(): LiveData<List<ThreeHourData>> = hours

    private fun loadHours(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val currentResponse = weatherRepository.getHoursByCoordinates(
                locationLat = latitude,
                locationLon = longitude,
                language = language

            )
            val listHoursData = converter.getHoursResponseToData(currentResponse)
            hours.postValue(listHoursData)

        }
    }

    //------------------------------------------Days--------------------------
    fun getDays(): LiveData<List<DayData>> = days

    fun loadDays(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val daysRepository =
                weatherRepository.daysWeatherByCoordinates(
                    locationLat = latitude,
                    locationLon = longitude,
                    language = language
                )
            days.postValue(converter.allDays(daysRepository, dayOfTheWeek = dayOfTheWeek))
        }
    }

    //--------------------------------places--------------------------------------------

    fun getLocations(): LiveData<List<LocationData>> = locationsLiveData

    private fun addLocationToDB(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.addPlace(converter.locationDataToPlaceEntity(locationData))
        }

    fun deleteLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.deletePlace(converter.locationDataToPlaceEntity(locationData))
        }

    fun getLocationByCoordinates(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val thisLocation = weatherRepository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude,
                language = language
            )
            val locationsData = LocationData(
                name = thisLocation.name,
                country = thisLocation.sys.country,
                latitude = thisLocation.coord.lat,
                longitude = thisLocation.coord.lon,
            )
            addLocationToDB(locationsData)
        }
    }


    fun addLocationToDB(locationName: String) =
        viewModelScope.launch {
            try {
                val forecast = weatherRepository.getCurrentWeatherByCity(locationName)
                    addLocationToDB(
                        converter.currentResponseToLocationData(
                            forecast,locationName
                        )
                    )
                    updateAllForecast(longitude = forecast.coord.lon, latitude = forecast.coord.lat)
                    appTitle.postValue(locationName)

            } catch (e: Exception) {

            }
        }

    fun getAppTitle():LiveData<String> = appTitle

    fun replaceLocation(locationData: LocationData) =
        viewModelScope.launch {
            placesRepository.addPlaceOrUpdate(converter.locationDataToPlaceEntity(locationData))
        }

    private fun updateAllForecast(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            loadCurrentWeather(longitude = longitude, latitude = latitude)
            loadDays(longitude = longitude, latitude = latitude)
            loadHours(longitude = longitude, latitude = latitude)
        }
    }

    fun updateAllForecast(locationData: LocationData) {
        updateAllForecast(locationData.longitude, locationData.latitude)
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