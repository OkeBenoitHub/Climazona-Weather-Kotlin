package com.www.climazona.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.www.climazona.R
import com.www.climazona.viewmodels.ForecastTabViewModel

class ForecastTabFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastTabFragment()
    }

    private lateinit var viewModel: ForecastTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}