package com.crossclassify.examlpeapp.simpleUsageExample

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.crossclassify.examlpeapp.MainViewModel
import com.crossclassify.examlpeapp.R
import com.crossclassify.examlpeapp.commonEpoxyExample.Epoxy2Activity
import com.crossclassify.examlpeapp.defaultEpoxyWithControllerExample.EpoxyActivity
import com.crossclassify.examlpeapp.defaultRecyclerViewExample.RecyclerActivity
import com.crossclassify.trackersdk.ScreenNavigationTracking
import com.crossclassify.trackersdk.TrackerActivity
import com.crossclassify.trackersdk.model.FieldMetaData
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//extend from TrackerActivity if you have form in activity and need form content and behavior analysis
class LoginActivity : TrackerActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    //override getFormName and define a name for your form
    override fun getFormName(): String = "user-login"

    //use it in case that you have recyclerView
    override fun getExternalMetaData(): List<FieldMetaData>? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //set onClickListener for your submit button and call trackerClickSubmitButton
        btnSubmit.setOnClickListener {
            trackerClickSubmitButton()
            showErrorDialog()
//            viewModel.checkAcc(editTextEmail.text.toString())
            Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_SHORT).show()
            clearSubmittedData()
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        goToRecycler.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
        }

        goToEpoxy.setOnClickListener {
            val intent = Intent(this, EpoxyActivity::class.java)
            startActivity(intent)
        }

        goToEpoxy2.setOnClickListener {
            val intent = Intent(this, Epoxy2Activity::class.java)
            startActivity(intent)
        }



        viewModel.checkAccountResult.observe(this) { result ->
            when (result) {
                true -> goToNextPage()
                false -> showErrorDialog()
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Your account not safe!")
            setMessage("Check account security")
            setPositiveButton("Ok") { dialog, _ -> dialog?.dismiss() }
        }.create().show()
    }

    private fun goToNextPage() {
        Toast.makeText(this, "Login accepted!", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            // TODO: Create intent for open next activity
        }
    }

    fun clearSubmittedData() {
        editTextEmail.setText("")
        editTextPassword.setText("")
        radio1.isChecked = false
        radio2.isChecked = false
        checkbox_choice1.isChecked = false
        checkbox_choice2.isChecked = false
    }


    override fun onResume() {
        super.onResume()
        //screen navigation tracking
        //pass activity path and title to trackNavigation method for screen navigation purposes
        ScreenNavigationTracking().trackNavigation("/activity_splash/activity_login", "Login")
    }


}



