package com.example.daydayweather.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daydayweather.R
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.HOUR_OF_DAY


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

    //--------------------------current Time ----------------------------------
    fun getCurrentWeather() = currentTime

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
            currentTemperature = currentTimeResponse.main.temp - Companion.absoluteZero,
            maxTemperature = currentTimeResponse.main.temp_max - Companion.absoluteZero,
            minTemperature = currentTimeResponse.main.temp_min - Companion.absoluteZero,
            Image = currentTimeResponse.weather[0].icon,
            description = currentTimeResponse.weather[0].description
        )
    }


    //-----------------------------------Hours----------------------------
    fun getHours(): LiveData<List<ThreeHourData>> = hours
    private val thisHour = Calendar.getInstance().get(HOUR_OF_DAY)

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
                    time = ((index * 3)+ thisHour)% 24,
                    Image = threeHours.weather[0].icon,
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
                name = dayOfTheWeek[(index - 1 + thisDay) % 7],
                condition = daily.weather[0].description,
                lowDegrees = daily.temp.min - Companion.absoluteZero,
                highDegrees = daily.temp.max - Companion.absoluteZero,
                image = daily.weather[0].icon
            )
            myDays.add(day)
        }
        return myDays.toList()
    }

    companion object {
        private const val absoluteZero: Double = 273.3
    }
}