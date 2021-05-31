package com.example.daydayweather.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.DAY_OF_WEEK


class MainViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val currentTime: MutableLiveData<CurrentTimeData> by lazy {
        MutableLiveData()
    }

    private val hours: MutableLiveData<List<ThreeHourData>> by lazy {
        MutableLiveData()
    }

    private val days: MutableLiveData<List<DayData>> by lazy {
        MutableLiveData()
    }

    private val absoluteZero: Double = 273.3
    //--------------------------current Time ----------------------------------
    fun getCurrentWeather(): LiveData<CurrentTimeData> = currentTime

    fun loadCurrentWeather(cityName: String) {
        viewModelScope.launch {
            val currentResponse = repository.getCurrentWeatherByCity(cityName)
            val currentData = convertCurrentResponseToData(currentResponse)
            currentTime.postValue(currentData)
        }
    }

    fun loadCurrentWeather(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            val currentResponse = repository.getCurrentWeatherByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val currentData = convertCurrentResponseToData(currentResponse)
            currentTime.postValue(currentData)
        }
    }


    private fun convertCurrentResponseToData(currentTimeResponse: CurrentTimeResponse): CurrentTimeData {

        return CurrentTimeData(
            currentTemperature = currentTimeResponse.main.temp - absoluteZero,
            maxTemperature = currentTimeResponse.main.temp_max - absoluteZero,
            minTemperature = currentTimeResponse.main.temp_min - absoluteZero,
            Image = 4,
            description = currentTimeResponse.weather[0].description
        )
    }

    //-----------------------------------Hours----------------------------
    fun getHours(): LiveData<List<ThreeHourData>> = hours

    fun loadHours(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val currentResponse = repository.getHoursByCoordinates(
                locationLat = latitude,
                locationLon = longitude
            )
            val listHoursData = convertToHoursData(currentResponse)
            hours.postValue(listHoursData)
        }
    }

    private fun convertToHoursData(threeHoursResponse: ThreeHoursResponse): List<ThreeHourData> {
        val hoursList: MutableList<ThreeHourData> = mutableListOf()
        threeHoursResponse.listThreeHours.forEachIndexed { index, threeHours ->
            hoursList.add(
                ThreeHourData(
                    pressure = threeHours.main.pressure,
                    humidity = threeHours.main.humidity,//לחות
                    windSpeed = threeHours.wind.speed,
                    visibility = threeHours.visibility, //נראות
                    TimeAndDat = threeHours.dt_txt,
                    description = threeHours.weather[0].description,
                    time = index * 3 % 24,
                    Image = 1,
                    degrees = threeHours.main.temp
                )
            )
        }
        return hoursList
    }


    //------------------------------------------Days--------------------------
    fun getDays(): LiveData<List<DayData>> = days

    fun loadDays(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val daysRepository =
                repository.daysWeatherByCoordinates(locationLat = latitude, locationLon = longitude)
            days.postValue(allDays(daysRepository))
        }
    }

    private val thisDay = Calendar.getInstance().get(DAY_OF_WEEK)
    private val dayOfTheWeek =
        listOf<String>("ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת")

    private fun allDays(daysResponse7: DaysResponse7): List<DayData> {
        val myDays = mutableListOf<DayData>()
        daysResponse7.daily.forEachIndexed { index, daily ->
            val day = DayData(
                name = dayOfTheWeek[(index + thisDay) % 7],
                condition = daily.weather[0].description,
                lowDegrees = daily.temp.min-absoluteZero,
                highDegrees = daily.temp.max-absoluteZero,
                image = 5
            )
            myDays.add(day)
        }
        return myDays.toList()
    }
}