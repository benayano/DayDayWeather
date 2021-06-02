package com.example.daydayweather.view

import com.example.daydayweather.R

class SetImages {
    companion object{
        fun getIcon(codeOfIcon:String):Int{
            return when(codeOfIcon){
                "01n"->(R.drawable.ic_delete_24)
                "02n"->(R.drawable.ic_down)
                "03n"->(R.drawable.ic_high)
                "04n"->(R.drawable.ic_launcher_background)
                "09n"->(R.drawable.ic_launcher_foreground)
                "10n"->(R.drawable.ic_sunset)
                "11n"->(R.drawable.ic_aaa)
                "13n"->(R.drawable.ic_aaa)
                "50n"->(R.drawable.ic_delete_24)
                else->(R.drawable.ic_aaa)
            }
        }
    }

}