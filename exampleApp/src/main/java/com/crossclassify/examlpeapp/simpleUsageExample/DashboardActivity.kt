package com.crossclassify.examlpeapp.simpleUsageExample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crossclassify.examlpeapp.R
import com.crossclassify.trackersdk.ScreenNavigationTracking
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        LogOut.setOnClickListener {
            val intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        ScreenNavigationTracking().trackNavigation("/activity_splash/activity_signup/activity_dashboard", "dashboard")
    }
}