package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.R
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory

class CurrentTimeFragment : Fragment(R.layout.fragment_current_day) {

    val viewModel: MainViewModel by viewModels {
        WeatherFactory(WeatherRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivCurrentDay: ImageView = view.findViewById(R.id.ivCurrentDay)
        val tvCurrentDegrees: TextView = view.findViewById(R.id.tvCurrentDegrees)
        val tvHighDegrees: TextView = view.findViewById(R.id.tvHighDegrees)
        val tvLowDegrees: TextView = view.findViewById(R.id.tvLowDegrees)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)



        // viewModel.loadCurrentWeather(longitude = 35.2224,latitude= 31.9421)
       // viewModel.loadCurrentWeather("בית אל")

        viewModel.getCurrentWeather().observe(viewLifecycleOwner) {
            ivCurrentDay.setImageResource(R.drawable.ic_launcher_background)
            tvCurrentDegrees.text = "${ "%.1f".format(it.currentTemperature) }°"
            tvHighDegrees.text = "${"%.0f".format(it.maxTemperature)}°"
            tvLowDegrees.text = "${"%.0f".format(it.minTemperature)}°"
            tvDescription.text = "${it.description}"
        }

    }

}
