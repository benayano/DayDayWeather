package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import com.example.daydayweather.R
import com.example.daydayweather.model.db.RoomCreator
import com.example.daydayweather.model.repository.LastCityChose
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.view.SetImages
import com.example.daydayweather.viewModel.LocationData
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory

class CurrentTimeFragment : Fragment(R.layout.fragment_current_time) {

    val viewModel: MainViewModel by activityViewModels {
        val placesDao = RoomCreator
            .getDbPlaces(requireContext())
            .getPlaceDao()
        val lastCityChose =
            LastCityChose(PreferenceManager.getDefaultSharedPreferences(this.requireContext()))

        WeatherFactory(
            WeatherRepository,
            PlacesRepository(placesDao),
            lastCityChose,
            language = getString(R.string.language),
            dayOfTheWeek = resources.getStringArray(R.array.days_of_week)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivCurrentDay: ImageView = view.findViewById(R.id.ivCurrentDay)
        val tvCurrentDegrees: TextView = view.findViewById(R.id.tvCurrentDegrees)
        val tvHighDegrees: TextView = view.findViewById(R.id.tvHighDegrees)
        val tvLowDegrees: TextView = view.findViewById(R.id.tvLowDegrees)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)



        viewModel.currentTime.observe(viewLifecycleOwner) {
            ivCurrentDay.setImageResource(SetImages.getIcon(it.Image))
            tvCurrentDegrees.text = "${"%.1f".format(it.currentTemperature)}??"
            tvHighDegrees.text = "${"%.0f".format(it.maxTemperature)}??"
            tvLowDegrees.text = "${"%.0f".format(it.minTemperature)}??"
            tvDescription.text = it.description
        }

        viewModel.loadLastCity()

        viewModel.getLastCity().observe(viewLifecycleOwner, {
            selectedLocation(it)
        })

    }

    private fun selectedLocation(locationData: LocationData) {
        viewModel.updateAllForecast(locationData)
        (activity as AppCompatActivity).supportActionBar?.title = locationData.name
    }

}
