package com.www.climazona.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.www.climazona.databinding.TodayFragmentBinding
import com.www.climazona.viewmodels.WeatherDataVMFactory
import com.www.climazona.viewmodels.WeatherDataViewModel

class TodayTabFragment : Fragment() {

    companion object {
        fun newInstance() = TodayTabFragment()
    }

    private lateinit var weatherDataViewModel: WeatherDataViewModel
    private lateinit var todayFragmentBinding: TodayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todayFragmentBinding = TodayFragmentBinding.inflate(inflater)
        // binds to this lifecycle owner
        todayFragmentBinding.lifecycleOwner = this
        return todayFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(activity).application
        val weatherDataVMFactory = WeatherDataVMFactory("Glenmont",null,null,null, application)
        weatherDataViewModel = ViewModelProvider(this, weatherDataVMFactory).get(WeatherDataViewModel::class.java)
        todayFragmentBinding.weatherDataViewModel = weatherDataViewModel

        weatherDataViewModel.weatherProperties.observe(viewLifecycleOwner, { weatherData ->
            weatherData?.let {
                todayFragmentBinding.result.text = weatherData.main.temp.toString()
            }
        })
    }

}