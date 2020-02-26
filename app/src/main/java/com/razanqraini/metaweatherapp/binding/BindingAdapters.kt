package com.razanqraini.metaweatherapp.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("refresh")
    fun refresh(swipeRefreshLayout: SwipeRefreshLayout, refresh: Boolean) {
        swipeRefreshLayout.isRefreshing = refresh
    }
}
