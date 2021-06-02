package com.example.daydayweather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.viewModel.LocationData

class LocationsAdapter : ListAdapter<LocationData, LocationsAdapter.ViewHolder>(LocationDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val locationView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.location,parent,false)
        return ViewHolder(locationView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        private val tvLatitude: TextView = itemView.findViewById(R.id.tvLatitude)
        private val tvLongitude: TextView = itemView.findViewById(R.id.tvLongitude)

        fun bind(locationData: LocationData) {
            tvCityName.text =locationData.name
            tvCountryName.text =locationData.country
            tvLatitude.text =locationData.latitude.toString()
            tvLongitude.text =locationData.longitude.toString()
        }
    }
}

class LocationDiffUtil : DiffUtil.ItemCallback<LocationData>() {
    override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
        return oldItem == newItem
    }

}