package com.example.daydayweather.view

import com.example.daydayweather.R

class SetImages {
    companion object{
        fun getIcon(codeOfIcon: String): Int {
            return when (codeOfIcon) {
                "01d" -> (R.drawable.ic_sun_fill)//clear sky
                "01n" -> (R.drawable.ic_moon_clear_line)//clear sky night
                "02d" -> (R.drawable.ic_sun_cloudy)//few clouds
                "02n" -> (R.drawable.ic_moon_cloudy_line)//few clouds night
                "03n" -> (R.drawable.ic_cloud_line)//scattered clouds
                "03d" -> (R.drawable.ic_cloud_line)//scattered clouds night
                "04d" -> (R.drawable.ic_rainy_line)//broken clouds
                "09d" -> (R.drawable.ic_broken_clouds)//rain
                "10d" -> (R.drawable.ic_shower_rain)//shower rain
                "11d" -> (R.drawable.ic_thunderstorms_line)//thunderstorm
                "13d" -> (R.drawable.ic_snowy_line)//snow
                "50d" -> (R.drawable.ic_mist_fill)//mist
                "04n" -> (R.drawable.ic_rainy_line)//broken clouds night
                "09n" -> (R.drawable.ic_broken_clouds)//rain night
                "10n" -> (R.drawable.ic_shower_rain)//shower rain night
                "11n" -> (R.drawable.ic_thunderstorms_line)//thunderstorm night
                "13n" -> (R.drawable.ic_snowy_line)//snow night
                "50n" -> (R.drawable.ic_mist_fill)//mist night
                else -> (R.drawable.ic_moon_clear_line)
            }
        }
    }

}