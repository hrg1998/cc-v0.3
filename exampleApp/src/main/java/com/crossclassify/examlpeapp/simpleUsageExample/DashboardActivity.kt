package com.crossclassify.examlpeapp.simpleUsageExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crossclassify.examlpeapp.R
import com.crossclassify.trackersdk.ScreenNavigationTracking

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    override fun onResume() {
        super.onResume()
        ScreenNavigationTracking().trackNavigation("/activity_splash/activity_signup/activity_dashboard", "dashboard")
    }
}