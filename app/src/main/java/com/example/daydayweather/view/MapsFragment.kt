package com.example.daydayweather.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.daydayweather.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val callback = OnMapReadyCallback { googleMap ->

        val Yitzhar = LatLng(
            32.1686,
            35.2337
        )
        googleMap.addMarker(MarkerOptions().position(Yitzhar).title("Marker in Yitzhar"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Yitzhar))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}