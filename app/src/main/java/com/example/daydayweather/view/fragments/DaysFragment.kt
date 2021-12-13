package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.model.db.RoomCreator
import com.example.daydayweather.model.repository.LastCityChose
import com.example.daydayweather.model.repository.PlacesRepository
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.view.adapters.DaysAdapter
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory

class DaysFragment : Fragment(R.layout.fragment_days) {


    private val mainViewModel: MainViewModel by activityViewModels {
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

    private val rvDays: RecyclerView by lazy { requireView().findViewById(R.id.rvDays) }
    private val daysAdapter: DaysAdapter by lazy { DaysAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDays.apply {
            adapter = daysAdapter.apply {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }


        mainViewModel.days.observe(viewLifecycleOwner, {
            daysAdapter.submitList(it)
        })

        mainViewModel.getLastCity().observe(viewLifecycleOwner, {
            mainViewModel.loadDays(longitude = it.longitude, latitude = it.latitude)

        })

    }

}