package com.example.daydayweather.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.daydayweather.R
import com.example.daydayweather.view.adapters.PagerAdapter
import com.example.daydayweather.view.fragments.CurrentTimeFragment
import com.example.daydayweather.view.fragments.DaysFragment
import com.example.daydayweather.view.fragments.HoursFragment
import com.google.android.gms.maps.MapFragment
import me.relex.circleindicator.CircleIndicator3


class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            CurrentTimeFragment(),
            HoursFragment(),
            DaysFragment()
           // MapFragment()
        )

        val adapterFragment = PagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager= view.findViewById<ViewPager2>(R.id.viewPagerFragments)
        viewPager.adapter = adapterFragment
        val indicator: CircleIndicator3 = view.findViewById(R.id.indicator)
        indicator.setViewPager(viewPager)

    }
}