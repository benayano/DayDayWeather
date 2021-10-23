package com.example.daydayweather.viewModel

import com.example.daydayweather.R
import com.example.daydayweather.model.db.PlacesEntity
import com.example.daydayweather.model.response.CurrentTimeResponse
import com.example.daydayweather.model.response.DaysResponse7
import com.example.daydayweather.model.response.ThreeHoursResponse
import java.util.*

class Converter {

    fun getIcon(codeOfIcon: String): Int {
        return when (codeOfIcon) {
            "01n" -> (R.drawable.ic_sun_fill)//clear sky
            "02n" -> (R.drawable.ic_sun_cloudy)//few clouds
            "03n" -> (R.drawable.ic_cloud_line)//scattered clouds
            "04n" -> (R.drawable.ic_rainy_line)//broken clouds
            "09n" -> (R.drawable.ic_broken_clouds)//rain
            "10n" -> (R.drawable.ic_shower_rain)//shower rain
            "11n" -> (R.drawable.ic_thunderstorms_line)//thunderstorm
            "13n" -> (R.drawable.ic_snowy_line)//snow
            "50n" -> (R.drawable.ic_mist_fill)//mist
            else -> (R.drawable.ic_moon_clear_line)
        }
    }

    fun currentResponseToLocationData(currentTimeResponse: CurrentTimeResponse):LocationData=
        LocationData(
            name = currentTimeResponse.name,
            country = loveIsrael(currentTimeResponse.sys.country),
            latitude = currentTimeResponse.coord.lat,
            longitude = currentTimeResponse.coord.lon
        )
    fun currentResponseToData(currentTimeResponse: CurrentTimeResponse): CurrentTimeData {
        return CurrentTimeData(
            currentTemperature = currentTimeResponse.main.temp - absoluteZero,
            maxTemperature = currentTimeResponse.main.temp_max - absoluteZero,
            minTemperature = currentTimeResponse.main.temp_min - absoluteZero,
            Image = currentTimeResponse.weather[0].icon,
            description = currentTimeResponse.weather[0].description
        )
    }

    fun getHoursResponseToData(threeHoursResponse: ThreeHoursResponse): List<ThreeHourData> {
        val hoursList: MutableList<ThreeHourData> = mutableListOf()
        val thisHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        threeHoursResponse.listThreeHours.forEachIndexed { index, threeHours ->
            hoursList.add(
                ThreeHourData(
                    pressure = threeHours.main.pressure,
                    humidity = threeHours.main.humidity,//לחות
                    windSpeed = threeHours.wind.speed,
                    visibility = threeHours.visibility, //נראות
                    timeAndDate = threeHours.dt_txt,
                    description = threeHours.weather[0].description,
                    time = ((index * 3) + thisHour) % 24,
                    Image = threeHours.weather[0].icon,
                    degrees = threeHours.main.temp

                )
            )
        }
        return hoursList
    }

    fun allDays(daysResponse7: DaysResponse7): List<DayData> {
        val myDays = mutableListOf<DayData>()
        val thisDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val dayOfTheWeek =
            listOf<String>("ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת")

        daysResponse7.daily.forEachIndexed { index, daily ->
            val day = DayData(
                name = dayOfTheWeek[(index - 1 + thisDay) % 7],
                condition = daily.weather[0].description,
                lowDegrees = daily.temp.min - absoluteZero,
                highDegrees = daily.temp.max - absoluteZero,
                image = daily.weather[0].icon
            )
            myDays.add(day)
        }
        return myDays.toList()
    }

    fun entityListToLocationList(entityList: List<PlacesEntity>): List<LocationData> {
        return entityList.mapNotNull { placesEntity ->
            placesEntity.takeIf { it.id != null }?.let {
                LocationData(
                    sirialNumber = it.id!!,
                    name = it.name,
                    country = loveIsrael(it.country),
                    longitude = it.longitude,
                    latitude = it.latitude
                )
            }
        }
    }

    private fun loveIsrael(countryName: String) = when (countryName) {
        "PS" -> "IL"
        else -> countryName
    }

    suspend fun locationDataToPlaceEntity(locationData: LocationData) = PlacesEntity(
        id = locationData.sirialNumber,
        name = locationData.name,
        country = locationData.country,
        longitude = locationData.longitude,
        latitude = locationData.latitude
    )


    companion object {
        private const val absoluteZero: Double = 273.3
    }


}