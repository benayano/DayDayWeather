package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.model.db.RoomCreator
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.view.adapters.HoursAdapter
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.ThreeHourData
import com.example.daydayweather.viewModel.WeatherFactory

class HoursFragment : Fragment(R.layout.fragment_hours) {

    private val viewModel: MainViewModel by activityViewModels {
        val placesDao = RoomCreator
            .getDbPlaces(requireContext())
            .getPlaceDao()
        WeatherFactory(WeatherRepository, PlacesRepository(placesDao))
    }


    private val rvHours: RecyclerView by lazy { requireView().findViewById(R.id.rvHours) }
    private val hoursAdapter: HoursAdapter by lazy { HoursAdapter(this::onChosenHour) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHours.apply {
            adapter = hoursAdapter.apply {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }


        viewModel.loadHours(longitude = 35.2224, latitude = 31.9421)
        viewModel.getHours().observe(
            viewLifecycleOwner, Observer {
                hoursAdapter.submitList(it)
            })

    }

    private fun onChosenHour(hourData: ThreeHourData) {
        val tvHumidity: TextView = requireView().findViewById(R.id.tvHumidity)
        val tvBarometricPressure: TextView =
            requireView().findViewById(R.id.tvBarometricPressure)
        val tvCloudy: TextView = requireView().findViewById(R.id.tvCloudy)
        val windSpeed: TextView = requireView().findViewById(R.id.windSpeed)
        val visibility: TextView = requireView().findViewById(R.id.visibility)
        val timeAndDate: TextView = requireView().findViewById(R.id.TimeAndDate)
        val description: TextView = requireView().findViewById(R.id.description)
        val time: TextView = requireView().findViewById(R.id.time)

        tvHumidity.text = "${getString(R.string.Humidity) + hourData.humidity.toString()}"
        tvBarometricPressure.text = getString(R.string.Pressure) + hourData.pressure.toString()
        tvCloudy.text = ""//getString(R.string.Cloudy) + hourData.cloudy.toString()""
        windSpeed.text = getString(R.string.windSpeed) + hourData.windSpeed.toString()
        visibility.text = getString(R.string.visibility) + hourData.visibility.toString()
        timeAndDate.text = getString(R.string.TimeAndDat) + hourData.timeAndDate
        description.text = hourData.description
        time.text = getString(R.string.time) + hourData.time.toString()
    }


}