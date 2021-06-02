package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.daydayweather.R
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory


class LocationFragment : Fragment(R.layout.fragment_location) {

    private val viewModel:MainViewModel by viewModels {
        WeatherFactory(WeatherRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText:EditText = view.findViewById(R.id.etLocation)
        val addButton:Button = view.findViewById(R.id.btnAddLocation)

        addButton.setOnClickListener{
            if (editText.text.isNotEmpty()){
                viewModel.loadCurrentWeather(editText.text.toString())
            }
        }


    }

}