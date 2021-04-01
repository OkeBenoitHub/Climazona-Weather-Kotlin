package com.www.climazona.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.www.climazona.R
import com.www.climazona.ui.fragments.ForecastTabFragment
import com.www.climazona.ui.fragments.TodayTabFragment
import com.www.climazona.ui.fragments.TomorrowTabFragment


class WeatherTabsAdapter(fm: FragmentManager, context: Context) :
    FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val tabTitles: Array<String> = arrayOf(
        context.getString(R.string.today_tab_text),
        context.getString(R.string.tomorrow_tab_text),
        context.getString(R.string.forecast_tab_text)
    )

    /**
     * Return the [Fragment] that should be displayed for the given page number.
     */
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TodayTabFragment()
            }
            1 -> {
                TomorrowTabFragment()
            }
            else -> {
                ForecastTabFragment()
            }
        }
    }

    /**
     * Return the total number of pages.
     */
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        // Generate title based on item position
        return tabTitles[position]
    }

}