package com.www.climazona.ui.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.www.climazona.R
import com.www.climazona.databinding.ActivityAboutAppBinding
import com.www.climazona.utils.APP_PROCESS_LOCATION_COMPLETED_PREF
import com.www.climazona.utils.MainUtil
import com.www.climazona.utils.SharedPrefUtil


class AboutAppActivity : AppCompatActivity() {
    private lateinit var activityAboutAppBinding: ActivityAboutAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAboutAppBinding = ActivityAboutAppBinding.inflate(layoutInflater)
        /**
         * Check if user has already allowed access to device location
         * If so, go directly to MainActivity to view weather data
         * If not, we show AboutAppActivity content
         */
        val isAppLocationRequestCompleted = SharedPrefUtil().getDataBooleanFromSharedPreferences(
            this,APP_PROCESS_LOCATION_COMPLETED_PREF)
        if (!isAppLocationRequestCompleted) {
            setContentView(activityAboutAppBinding.root)
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Change action Bar background color
        MainUtil().setActionBarBackgroundColor(this, supportActionBar, R.color.colorPrimary)

        // Set navigation bottom background color
        MainUtil().setBottomBarNavigationBackgroundColor(
            window = window,
            this,
            R.color.colorPrimary,
            R.color.bottom_black_color
        )

        // report issue button tapped
        activityAboutAppBinding.reportIssueActionBtn.setOnClickListener {
            // send report via email
            MainUtil().composeEmail(
                this,
                arrayOf(getString(R.string.email_app_for_reports_and_feedback)),
                getString(R.string.report_an_issue_text),
                getString(R.string.tell_us_about_issue_text),
                getString(R.string.report_issue_via_text)
            )
        }

        // send feedback button tapped
        activityAboutAppBinding.feedbackActionBtn.setOnClickListener {
            // send feedback via email
            MainUtil().composeEmail(
                this,
                arrayOf(getString(R.string.email_app_for_reports_and_feedback)),
                getString(R.string.feedback_menu_text),
                getString(R.string.tell_us_how_to_improve_app),
                getString(R.string.send_feedback_via_text)
            )
        }

        // access weather button tapped
        activityAboutAppBinding.accessWeatherBtn.setOnClickListener {
            // request user location before accessing weather data
            Dexter.withContext(this@AboutAppActivity)
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                // mark location request as completed in prefs
                                SharedPrefUtil().writeDataBooleanToSharedPreferences(applicationContext,
                                    APP_PROCESS_LOCATION_COMPLETED_PREF, true)
                                // go to MainActivity
                                startActivity(intent)
                            } else {
                                MainUtil().displaySnackBarMessage(
                                    activityAboutAppBinding.mainLayout,
                                    R.string.location_permission_warning_text,
                                    Snackbar.LENGTH_INDEFINITE
                                )
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        // Remember to invoke this method when the custom rationale is closed
                        // or just by default if you don't want to use any custom rationale.
                        token?.continuePermissionRequest()
                    }
                })
                .withErrorListener {
                    MainUtil().showToastMessage(applicationContext, it.name)
                }
                .onSameThread()
                .check()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_app) {
            var textToShare = getString(R.string.text_share_app_1) + " "
            textToShare += getString(R.string.text_share_app_2) + "\n"
            textToShare += getString(R.string.app_store_link_text)
            MainUtil().shareTextData(
                this,
                getString(R.string.share_app_dialog_title_text),
                textToShare
            )
        }
        return super.onOptionsItemSelected(item)
    }
}