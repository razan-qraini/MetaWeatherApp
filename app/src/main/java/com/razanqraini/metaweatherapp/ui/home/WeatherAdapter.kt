package com.razanqraini.metaweatherapp.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class WeatherAdapter(
    fragmentActivity: FragmentActivity,
    private val locationsInfoList: List<String>,
    private val pagesOffset: Int
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return locationsInfoList.size
    }

    override fun createFragment(position: Int): Fragment {
        return WeatherFragment.newInstance(position, locationsInfoList[position])
    }
}