package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.model.db.RoomCreator
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.view.adapters.HoursAdapter
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory

class HoursFragment : Fragment(R.layout.fragment_hours) {

    private val viewModel: MainViewModel by activityViewModels {
        val placesDao = RoomCreator
            .getDbPlaces(requireContext())
            .getPlaceDao()
        WeatherFactory(WeatherRepository, PlacesRepository(placesDao))
    }

    private val rvHours: RecyclerView by lazy { requireView().findViewById(R.id.rvHours) }
    private val hoursAdapter: HoursAdapter by lazy { HoursAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHours.apply {
            adapter = hoursAdapter.apply {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        viewModel.loadHours(longitude = 35.2224,latitude= 31.9421)
        viewModel.getHours().observe(
            viewLifecycleOwner, Observer {
                hoursAdapter.submitList(it)
            })

    }

}