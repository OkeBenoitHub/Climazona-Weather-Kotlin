package com.www.climazona.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.www.climazona.R
import com.www.climazona.adapters.WeatherTabsAdapter
import com.www.climazona.utils.MainUtil
import com.www.climazona.utils.NAV_DRAWER_ITEM_SELECTED_KEY_PREF
import com.www.climazona.utils.SharedPrefUtil

class MyLocationFragment : Fragment() {
    private lateinit var weatherTabsAdapter: WeatherTabsAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherTabsAdapter =
            WeatherTabsAdapter(requireActivity().supportFragmentManager, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // save navigation drawer item selected key to preferences
        SharedPrefUtil().writeDataStringToSharedPreferences(
            requireContext(), NAV_DRAWER_ITEM_SELECTED_KEY_PREF,
            "nav_my_location")
        setUpWeatherTabsAdapterWithViewPager(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.adapter = null
    }

    override fun onDetach() {
        super.onDetach()
        viewPager.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.adapter = null
    }

    /**
     * Set up weather tabs adapter with viewpager
     * @param rootView :: root parent view
     */
    private fun setUpWeatherTabsAdapterWithViewPager(rootView: View) {
        // Create an adapter that knows which fragment should be shown on each page
        viewPager = rootView.findViewById(R.id.view_pager)
        // Give the TabLayout the ViewPager
        tabLayout = rootView.findViewById(R.id.tab_layout)
        // Set the adapter onto the view pager
        viewPager.adapter = weatherTabsAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}