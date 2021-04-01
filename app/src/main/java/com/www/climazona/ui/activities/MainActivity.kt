package com.www.climazona.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.www.climazona.R
import com.www.climazona.utils.MainUtil
import com.www.climazona.utils.NAV_DRAWER_ITEM_SELECTED_KEY_PREF
import com.www.climazona.utils.SharedPrefUtil


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_my_location,
                R.id.nav_other_locations,
                R.id.nav_share_app,
                R.id.nav_feedback,
                R.id.nav_report_issue
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
            val navDrawerItemSelectedKey = SharedPrefUtil().getDataStringFromSharedPreferences(applicationContext, NAV_DRAWER_ITEM_SELECTED_KEY_PREF)
            when (menuItem.itemId) {
                // nav my location
                R.id.nav_my_location -> {
                    if (navDrawerItemSelectedKey == "nav_my_location") {
                        finish()
                        startActivity(intent)
                    }
                }
                // nav other locations
                R.id.nav_other_locations -> {
                    navView.menu.findItem(R.id.nav_other_locations).isCheckable = true
                }
                // nav share app
                R.id.nav_share_app -> {
                    // prevent nav share app to be checked
                    navView.menu.findItem(R.id.nav_share_app).isCheckable = false
                    // share app
                    var textToShare = getString(R.string.text_share_app_1) + " "
                    textToShare += getString(R.string.text_share_app_2) + "\n"
                    textToShare += getString(R.string.app_store_link_text)
                    MainUtil().shareTextData(
                        this,
                        getString(R.string.share_app_dialog_title_text),
                        textToShare
                    )
                }
                // nav feedback
                R.id.nav_feedback -> {
                    // prevent nav share app to be checked
                    navView.menu.findItem(R.id.nav_feedback).isCheckable = false
                    // send feedback via email
                    MainUtil().composeEmail(
                        this,
                        arrayOf(getString(R.string.email_app_for_reports_and_feedback)),
                        getString(R.string.feedback_menu_text),
                        getString(R.string.tell_us_how_to_improve_app),
                        getString(R.string.send_feedback_via_text)
                    )
                }
                // nav report an issue
                R.id.nav_report_issue -> {
                    // prevent nav share app to be checked
                    navView.menu.findItem(R.id.nav_report_issue).isCheckable = false
                    // send report via email
                    MainUtil().composeEmail(
                        this,
                        arrayOf(getString(R.string.email_app_for_reports_and_feedback)),
                        getString(R.string.report_an_issue_text),
                        getString(R.string.tell_us_about_issue_text),
                        getString(R.string.report_issue_via_text)
                    )
                }
            }
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}