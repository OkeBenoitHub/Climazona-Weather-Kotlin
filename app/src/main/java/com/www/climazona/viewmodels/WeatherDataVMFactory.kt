package com.www.climazona.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Simple ViewModel factory that provides the WeatherData and context to the ViewModel.
 */
class WeatherDataVMFactory(
    private val cityName: String?,
    private val lat: Double?,
    private val lon: Double?,
    private val zip: Int?,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDataViewModel::class.java)) {
            return WeatherDataViewModel(cityName, lat, lon, zip, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}