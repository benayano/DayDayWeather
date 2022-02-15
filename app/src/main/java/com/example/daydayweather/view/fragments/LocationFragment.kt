package com.example.daydayweather.view.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task


class LocationFragment : Fragment(R.layout.fragment_location) {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        ivMyLocation = view.findViewById(R.id.ivMyLocation)
        val rvLocation: RecyclerView = view.findViewById(R.id.rvLocation)
        val editText: EditText = view.findViewById(R.id.etLocation)
        val addButton: Button = view.findViewById(R.id.btnAddLocation)

        rvLocation.adapter = locationAdapter
        rvLocation.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getLocations().observe(viewLifecycleOwner) {
            locationAdapter.submitList(it)
        }

        addButton.setOnClickListener {
            if (formatLocation(editText.text.toString()).isNotEmpty()){
               addLocation(formatLocation(editText.text.toString()))
                editText.text.clear()
            } else {
                Toast.makeText(requireContext(), "pleas enter valid City", Toast.LENGTH_SHORT)
                    .show()
                editText.text.clear()
            }
        }

        ivMyLocation.setOnClickListener {
            getThisLocation()
        }

    }

    private fun locationPermissionGreats(): Boolean = ContextCompat.checkSelfPermission(
        requireActivity().applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        requireActivity().applicationContext,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    @SuppressLint("MissingPermission")
    private fun getThisLocation() {
        if (locationPermissionGreats()) {
            useLocation()
        } else {
            requestPermissionLocation().also {
                makeToast("the app dose not gave permissions to access the location")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun useLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        if (task.isSuccessful) {
            task.addOnSuccessListener {
                viewModel.getLocationByCoordinates(
                    longitude = it.longitude,
                    latitude = it.latitude
                ).also {
                    makeToast("We can find your location\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01")
                }

            }
        } else
            makeToast("We can't find your location")

    }

    private fun makeToast(str: String) = Toast.makeText(
        requireContext(),
        str,
        Toast.LENGTH_SHORT
    ).show()

    private fun requestPermissionLocation() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            locationPermission,
            PERMISSION_REQUEST_CODE
        )
    }

    private fun selectedLocation(locationData: LocationData) {
        val selectedName = locationData.name

        viewModel.saveLastCity(locationData)
        viewModel.updateAllForecast(locationData)
        Toast.makeText(requireContext(), " $selectedName is selected", Toast.LENGTH_SHORT).show()
        (activity as AppCompatActivity).supportActionBar?.title = selectedName
    }
    private fun addLocation(cityName:String){
        viewModel.addLocationToDB(cityName)
    }

    private fun deleteLocationItem(locationData: LocationData) =
        viewModel.deleteLocation(locationData)

    private fun formatLocation(nameLocation: String) =
        nameLocation.replace("\\s+".toRegex(), " ").trim { it.isWhitespace() }

}