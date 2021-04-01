package com.www.climazona.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.www.climazona.R
import com.www.climazona.utils.NAV_DRAWER_ITEM_SELECTED_KEY_PREF
import com.www.climazona.utils.SharedPrefUtil
import com.www.climazona.viewmodels.OtherLocationsViewModel

class OtherLocationsFragment : Fragment() {

    private lateinit var mOtherLocationsViewModel: OtherLocationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.other_locations_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // save navigation drawer item selected key to preferences
        SharedPrefUtil().writeDataStringToSharedPreferences(
            requireContext(), NAV_DRAWER_ITEM_SELECTED_KEY_PREF,
            "nav_other_locations")
    }
}