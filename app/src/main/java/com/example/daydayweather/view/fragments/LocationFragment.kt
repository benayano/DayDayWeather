package com.example.daydayweather.view.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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
import com.example.daydayweather.view.adapters.LocationsAdapter
import com.example.daydayweather.viewModel.LocationData
import com.example.daydayweather.viewModel.MainViewModel
import com.example.daydayweather.viewModel.WeatherFactory


class LocationFragment : Fragment(R.layout.fragment_location) {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 15
    }

    private val viewModel by activityViewModels<MainViewModel> {
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

    private val locationAdapter by lazy {
        LocationsAdapter(
            selectedLocation = this::selectedLocation,
            deleteItem = this::deleteLocationItem
        )
    }

    lateinit var ivMyLocation: ImageView
    private lateinit var locationManager: LocationManager

    private val locationPermission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        ivMyLocation = view.findViewById(R.id.ivMyLocation)
        val rvLocation: RecyclerView = view.findViewById(R.id.rvLocation)
        val editText: EditText = view.findViewById(R.id.etLocation)
        val addButton: Button = view.findViewById(R.id.btnAddLocation)

        rvLocation.adapter = locationAdapter
        rvLocation.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getLocations().observe(viewLifecycleOwner, {
            locationAdapter.submitList(it)
        })

        addButton.setOnClickListener {
            if (formatLocation(editText.text.toString()).isNotEmpty()) {
                val name = formatLocation(editText.text.toString())
                viewModel.addLocation(name)
                editText.text.clear()
            }
        }

        ivMyLocation.setOnClickListener {
                getLocation()
        }

    }

    private fun locationPermissionGreats(): Boolean = ContextCompat.checkSelfPermission(
        requireActivity().applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(
        requireActivity().applicationContext,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_DENIED


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                            viewModel.updateAllForecast(it.longitude,it.latitude)
                        }
                        // Precise location access granted.
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.let {
                            viewModel.updateAllForecast(it.longitude,it.latitude)
                        }
                    }
                    else -> {
                        // No location access granted.
                    }
                }
            }

        }
    }

    private fun selectedLocation(locationData: LocationData) {
        val selectedName = locationData.name

        viewModel.saveLastCity(locationData)
        viewModel.updateAllForecast(locationData)
        Toast.makeText(requireContext(), " $selectedName is selected", Toast.LENGTH_SHORT).show()
        (activity as AppCompatActivity).supportActionBar?.title = selectedName
    }

    private fun deleteLocationItem(locationData: LocationData) =
        viewModel.deleteLocation(locationData)

    private fun formatLocation(nameLocation: String) =
        nameLocation.replace("\\s+".toRegex(), " ").trim { it.isWhitespace() }

}