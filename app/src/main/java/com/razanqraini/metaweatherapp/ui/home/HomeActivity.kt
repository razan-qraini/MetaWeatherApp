package com.razanqraini.metaweatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razanqraini.metaweatherapp.R
import com.razanqraini.metaweatherapp.databinding.ActivityHomeBinding
import com.razanqraini.metaweatherapp.utils.EventObserver
import com.razanqraini.metaweatherapp.utils.extensions.setupToolbar
import com.razanqraini.metaweatherapp.utils.extensions.*

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy { viewModel<HomeViewModel>() }

    private lateinit var binding: ActivityHomeBinding

    private lateinit var locationsAdapter: LocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        setupToolbar()
        locationsAdapter = LocationsAdapter {
            // TODO: open details activity
        }
        with(binding) {
            lifecycleOwner = this@HomeActivity
            viewModel = this@HomeActivity.viewModel
            with(locationsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = locationsAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
                setHasFixedSize(true)
            }
        }

    }

    private fun initViewModel() {
        with(viewModel) {
            locationsListLiveData.observe(this@HomeActivity, Observer {
                locationsAdapter.submitList(it)
            })
            errorEventLiveData.observe(this@HomeActivity, EventObserver {
                // TODO: show an error message
            })
        }
    }
}
