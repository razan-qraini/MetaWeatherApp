package com.razanqraini.metaweatherapp.utils.extensions

import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.razanqraini.metaweatherapp.di.viewmodel.ViewModelFactoryProvider

/**
 * Returns the view model that will be used in this activity.
 *
 * Note that the view model must be declared in [ViewModelsModule] to be provided,
 * or else [IllegalArgumentException] will be thrown.
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModel(): VM {
    return ViewModelProviders.of(this, ViewModelFactoryProvider(this).viewModelFactory)
        .get(VM::class.java)
}

/**
 * Returns the view model that will be used in this Fragment.
 *
 * Note that the view model must be declared in [ViewModelsModule] to be provided,
 * or else [IllegalArgumentException] will be thrown.
 */
inline fun <reified VM : ViewModel> Fragment.viewModel(): VM {
    return ViewModelProviders.of(this, ViewModelFactoryProvider(context!!).viewModelFactory)
        .get(VM::class.java)
}

fun <V : ViewDataBinding> Fragment.inflateBinding(
    layoutRes: Int,
    viewGroup: ViewGroup?,
    dataBindingComponent: DataBindingComponent? = null
): V {
    return DataBindingUtil.inflate(
        layoutInflater,
        layoutRes,
        viewGroup,
        false,
        dataBindingComponent
    )
}