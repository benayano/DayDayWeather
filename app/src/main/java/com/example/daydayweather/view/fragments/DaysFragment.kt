package com.example.daydayweather.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.model.repository.WeatherRepository
import com.example.daydayweather.view.adapters.DaysAdapter
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory

class DaysFragment : Fragment(R.layout.fragment_days) {
    private val mainViewModel: MainViewModel by viewModels{
        WeatherFactory(WeatherRepository)
    }

    private val rvDays :RecyclerView by lazy { requireView().findViewById(R.id.rvDays) }
    private val daysAdapter: DaysAdapter by lazy { DaysAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDays.apply {
            adapter = daysAdapter.apply {
                layoutManager =LinearLayoutManager(requireContext())
            }
        }

        mainViewModel.loadDays(longitude = 35.2224,latitude= 31.9421)
        mainViewModel.getDays().observe(viewLifecycleOwner,{
            daysAdapter.submitList(it)
        })


    }
    
}