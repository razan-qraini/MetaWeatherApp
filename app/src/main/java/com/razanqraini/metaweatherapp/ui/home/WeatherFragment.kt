package com.razanqraini.metaweatherapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.razanqraini.metaweatherapp.R
import com.razanqraini.metaweatherapp.binding.PhotoManagerDataBindingComponent
import com.razanqraini.metaweatherapp.databinding.FragmentWeatherBinding
import com.razanqraini.metaweatherapp.di.Injectable
import com.razanqraini.metaweatherapp.utils.EventObserver
import com.razanqraini.metaweatherapp.utils.extensions.viewModel
import kotlinx.android.synthetic.main.fragment_weather.view.*
import javax.inject.Inject

class WeatherFragment : Fragment(), Injectable {

    private val viewModel by lazy { viewModel<WeatherFragmentViewModel>() }

    private lateinit var binding: FragmentWeatherBinding

    @Inject
    lateinit var photoManagerDataBindingComponent: PhotoManagerDataBindingComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_weather,
                container,
                false,
                photoManagerDataBindingComponent
            )
        initViews()
        initViewModel()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WeatherFragment.viewModel
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            loadLocationInfo(getLocationName(), false)
            locationInfoLiveData.observe(viewLifecycleOwner, Observer {
                binding.locationInfoItem = it
                // Display tomorrow's weather - second item in the list
                binding.consolidatedWeatherItem = it.consolidatedWeather[1]
            })
        }
    }

    private fun getPosition(): Int =
        arguments?.getInt(EXTRA_POSITION)
            ?: throw IllegalArgumentException("$EXTRA_POSITION must be provided")

    private fun getLocationName(): String =
        arguments?.getString(EXTRA_LOCATION_INFO)
            ?: throw IllegalArgumentException("$EXTRA_LOCATION_INFO must be provided")

    companion object {

        private val EXTRA_POSITION = WeatherFragment::class.java.name + "_POSITION_EXTRA"

        private val EXTRA_LOCATION_INFO =
            WeatherFragment::class.java.name + "_LOCATION_INFO_EXTRA"

        fun newInstance(position: Int, locationName: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_POSITION, position)
                    putString(EXTRA_LOCATION_INFO, locationName)
                }
            }
    }
}