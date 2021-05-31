package com.example.daydayweather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daydayweather.R
import com.example.daydayweather.viewModel.ThreeHourData

class HoursAdapter : ListAdapter<ThreeHourData, HoursAdapter.ViewHolder>(HoursDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val hourView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.hour, parent, false)
        return ViewHolder(hourView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourImageView: ImageView = itemView.findViewById(R.id.ivSunset)
        private val hourTime: TextView = itemView.findViewById(R.id.tvHour)
        private val hourDegrees: TextView = itemView.findViewById(R.id.tvDegrees)
        fun bind(threeHourData: ThreeHourData) {
            hourTime.text = threeHourData.time.toString()
            hourDegrees.text = "${"%.1f".format(threeHourData.degrees-273.3)}°"
            //hourImageView.setImageResource(R.drawable.ic_sunset)

        }
    }

}

class HoursDiffUtil : DiffUtil.ItemCallback<ThreeHourData>() {
    override fun areItemsTheSame(oldItem: ThreeHourData, newItem: ThreeHourData): Boolean {
        return oldItem === newItem
    }
    override fun areContentsTheSame(oldItem: ThreeHourData, newItem: ThreeHourData): Boolean {
        return oldItem == newItem
    }
}
