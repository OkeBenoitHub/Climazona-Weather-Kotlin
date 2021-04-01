package com.www.climazona.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.www.climazona.R
import com.www.climazona.network.WeatherApi
import com.www.climazona.network.WeatherApiUnits
import com.www.climazona.network.WeatherProperty
import kotlinx.coroutines.launch

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class WeatherDataViewModel(cityName: String?,lat: Double?, lon: Double?, zip: Int?, application: Application) : AndroidViewModel(application) {

    private val app = application
    private val city = cityName
    private val latitude = lat
    private val longitude = lon
    private val zipCode = zip

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<WeatherApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<WeatherApiStatus>
        get() = _status

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // Internally, we use a MutableLiveData, because we will be updating weather data
    private val _weatherProperties = MutableLiveData<WeatherProperty?>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val weatherProperties: LiveData<WeatherProperty?>
        get() = _weatherProperties


    /**
     * Call getCurrentWeatherDataProperties() on init so we can display status immediately.
     */
    init {
        getCurrentWeatherDataProperties(WeatherApiUnits.FAHRENHEIT)
    }

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    private fun getCurrentWeatherDataProperties(units: WeatherApiUnits) {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING
            try {
                if (city != null) {
                    _weatherProperties.value = WeatherApi.retrofitService.getWeatherDataByCityName(
                        city, units.value, "en", app.getString(R.string.api_key_text)
                    )
                }
                _status.value = WeatherApiStatus.DONE
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _status.value = WeatherApiStatus.ERROR
                _weatherProperties.value = null
            }
        }
    }
}