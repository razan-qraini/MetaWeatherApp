package com.razanqraini.metaweatherapp.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo

class LocationsAdapter(
    fragmentActivity: FragmentActivity,
    private val locationsInfoList: List<LocationInfo>,
    private val pagesOffset: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        // todo pass item
        return EntriesFragment.newInstance(position, locationsInfoList[position])
    }
}