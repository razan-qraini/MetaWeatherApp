package com.razanqraini.metaweatherapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.razanqraini.metaweatherapp.R
import com.razanqraini.metaweatherapp.databinding.ActivityHomeBinding
import com.razanqraini.metaweatherapp.utils.EventObserver
import com.razanqraini.metaweatherapp.utils.extensions.setupToolbar
import com.razanqraini.metaweatherapp.utils.extensions.viewModel
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy { viewModel<HomeViewModel>() }

    private lateinit var binding: ActivityHomeBinding

    private lateinit var pagerAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        setupToolbar(displayHomeAsUpEnabled = false)
        with(binding) {
            lifecycleOwner = this@HomeActivity
            viewModel = this@HomeActivity.viewModel
            with(viewPager) {
                offscreenPageLimit = 1
                registerOnPageChangeCallback(onPageChangeCallback)
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            locationsListLiveData.observe(this@HomeActivity, Observer {
                pagerAdapter = WeatherAdapter(
                    this@HomeActivity, it, VIEW_PAGER_OFFSET
                )
                binding.viewPager.apply {
                    adapter = pagerAdapter
                    offscreenPageLimit = 1
                }
            })
            errorEventLiveData.observe(this@HomeActivity, EventObserver {
                // TODO: show an error message
            })
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Timber.d(">>> onPageSelected : $position")
        }
    }

    companion object {
        private const val VIEW_PAGER_OFFSET = Int.MAX_VALUE / 2

        fun newIntent(
            context: Context
        ) = Intent(context, HomeActivity::class.java)
    }
}
