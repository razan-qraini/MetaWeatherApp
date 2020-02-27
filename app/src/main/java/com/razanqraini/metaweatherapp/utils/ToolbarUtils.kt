package com.razanqraini.metaweatherapp.utils

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.razanqraini.metaweatherapp.R


object ToolbarUtils {

    /**
     * Sets up the tool bar for the provided activity, with action-up enabled.
     * The activity should include the layout R.layout.tool_bar in it's view, or should
     * have a toolbar with the id: R.id.toolbar.
     * And if the activity has a translucent status-bar style,
     * then the tool bar will be pushed below the status bar, by adding the appropriate top margin.
     */
    @JvmOverloads
    fun setupToolbarForActivity(
        activity: AppCompatActivity,
        translucentStatusBar: Boolean = false,
        displayHomeAsUpEnabled: Boolean = true,
        apply: Toolbar.() -> Unit = {}
    ) {
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        setupToolbarForActivity(toolbar, activity, translucentStatusBar, displayHomeAsUpEnabled)
        apply(toolbar)
    }

    @JvmOverloads
    fun setupToolbarForFragment(
        fragment: Fragment,
        toolbar: Toolbar?,
        displayHomeAsUpEnabled: Boolean = false,
        translucentStatusBar: Boolean = false,
        apply: (Toolbar) -> Unit = {}
    ) {
        if (toolbar != null && fragment.activity != null) {
            setupToolbarForActivity(
                toolbar,
                fragment.activity as AppCompatActivity,
                translucentStatusBar,
                displayHomeAsUpEnabled
            )
        }
        toolbar?.let {
            apply(it)
        }
    }

    private fun setupToolbarForActivity(
        toolbar: Toolbar,
        activity: AppCompatActivity,
        translucentStatusBar: Boolean,
        displayHomeAsUpEnabled: Boolean = true
    ) {
        if (translucentStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val marginLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
            marginLayoutParams.setMargins(
                marginLayoutParams.marginStart,
                marginLayoutParams.topMargin + getStatusBarHeight(activity),
                marginLayoutParams.marginEnd,
                marginLayoutParams.bottomMargin

            )
        }

        activity.setSupportActionBar(toolbar)

        val actionBar = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
    }

    /**
     * Returns the height of the status bar in pixels, based on the current device configuration(Orientation, Language, Screen Density, etc... ).
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * Returns the height of the navigation bar in pixels, based on the current device configuration(Orientation, Language, Screen Density, etc... ).
     */
    fun getNavigationBarHeight(context: Context): Int {
        var navigationBarHeight = 0
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            navigationBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return navigationBarHeight
    }
}