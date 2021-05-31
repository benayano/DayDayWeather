package com.example.daydayweather.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    val fragmentList: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount()=fragmentList.size
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}