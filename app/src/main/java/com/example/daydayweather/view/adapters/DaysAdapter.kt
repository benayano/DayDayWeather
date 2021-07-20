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
import com.example.daydayweather.view.SetImages
import com.example.daydayweather.viewModel.DayData

class DaysAdapter : ListAdapter<DayData, DaysAdapter.ViewHolder>(DaysDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dayView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.day, parent, false)
        return ViewHolder(dayView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamOfDay: TextView = itemView.findViewById(R.id.tvNamOfDay)
        private val tvConditionDay: TextView = itemView.findViewById(R.id.tvConditionDay)
        private val ivMiddleDay: ImageView = itemView.findViewById(R.id.ivMiddleDay)
        private val tvHighDayDegrees: TextView = itemView.findViewById(R.id.tvHighDayDegrees)
        private val tvLowDayDegrees: TextView = itemView.findViewById(R.id.tvLowDayDegrees)

        fun bind(dayData: DayData) {
            tvNamOfDay.text = dayData.name
            tvConditionDay.text  = dayData.condition
            tvHighDayDegrees.text  = "${ "%.0f".format(dayData.highDegrees) }°"
            tvLowDayDegrees.text  = "${ "%.0f".format(dayData.lowDegrees) }°"
            ivMiddleDay.setImageResource(SetImages.getIcon(dayData.image))

        }

    }

}

class DaysDiffUtil : DiffUtil.ItemCallback<DayData>() {
    override fun areItemsTheSame(oldItem: DayData, newItem: DayData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DayData, newItem: DayData): Boolean {
        return oldItem == newItem
    }

}
