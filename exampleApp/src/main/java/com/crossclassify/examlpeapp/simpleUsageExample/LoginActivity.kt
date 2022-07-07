package com.crossclassify.examlpeapp.simpleUsageExample

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.crossclassify.examlpeapp.MainViewModel
import com.crossclassify.examlpeapp.R
import com.crossclassify.examlpeapp.commonEpoxyExample.Epoxy2Activity
import com.crossclassify.examlpeapp.defaultEpoxyWithControllerExample.EpoxyActivity
import com.crossclassify.examlpeapp.defaultRecyclerViewExample.RecyclerActivity
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModelForDev
import com.crossclassify.trackersdk.ScreenNavigationTracking
import com.crossclassify.trackersdk.TrackerActivity
import com.crossclassify.trackersdk.model.FieldMetaData
import com.crossclassify.trackersdk.utils.objects.Values
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//extend from TrackerActivity if you have form in activity and need form content and behavior analysis
class LoginActivity : TrackerActivity() {
    private val apiMode=arrayOf("dev","prod","stg")
    private var email:String=""
    private var mode : Int = 0
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var id = ""
    private var dialog: AlertDialog? = null
    private var loading = false
        set(value) {
            field = value
            when (value) {
                true -> layoutLoading.visibility = View.VISIBLE
                false -> layoutLoading.visibility = View.GONE
            }
        }

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
            loading = true
            email= editTextEmail.text.toString()
            viewModel.createAcc(editTextEmail.text.toString())
            trackerClickSubmitButton()
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



        viewModel.checkCreateAccountResult.observe(this) { result ->
            when(result){
                is CheckAccountResponseModel ->{
                    if (result._error != null && result._error.code == 409) {
                        result._error.message.decision?.let {
                            when (it) {
                                "approve" -> {
                                    goToNextPage()
                                }
                                "block" -> {
                                    showErrorDialog("Blocked", "You can't open this account")
                                }
                                "call_for_calc" -> {
                                    viewModel.checkAcc(result._error.message._id)
                                }
                            }
                        }
                    } else {
                        id = result._id
                        viewModel.checkAcc(result._id)
                    }
                }
                is CheckAccountResponseModelForDev-> {
                    if (result._error != null && result._error.code == 409) {
                        result._error.massage.isBlocked.let {
                            when (it) {
                                true -> {
                                    showErrorDialog("Blocked", "You can't open this account")
                                }
                                false -> {
                                    goToNextPage()

                                }
                            }
                        }
                    } else {
                        id = result._id
                        viewModel.checkAcc(result._id)
                    }
                }
                is Int ->{
                    loading = if(result==403){
                        showErrorDialog("Error", "Please connect with VPN")
                        editTextEmail.setText(email)

                        false
                    }else{
                        showErrorDialog("Error", "Please try again later")
                        false
                    }
                }
            }

        }

        viewModel.checkAccountResult.observe(this) { result ->
            when (result){
                is CheckAccountResponseModel ->{
                    when (result.status) {
                        "ready" -> {
                            when(result.decision){
                                "approve" -> {
                                    goToNextPage()
                                }
                                "block" -> {
                                    showErrorDialog("Blocked", "You can't open this account")
                                }
                            }
                            loading = false
                        }
                        "call_for_calc" -> {
                            showErrorDialog(
                                "Please wait!",
                                "Verifying account..."
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                delay(5000)
                                viewModel.checkAcc(id)
                            }
                        }
                    }
                }
                is CheckAccountResponseModelForDev ->{
                    when (result.status) {
                        "ready" -> {
                            when(result.isBlocked){
                                true -> {
                                    showErrorDialog("Blocked", "You can't open this account")
                                }
                                false -> {
                                    goToNextPage()
                                }
                            }
                            loading = false
                        }
                        "call_for_calc" -> {
                            showErrorDialog(
                                "Please wait!",
                                "Check account security.\nPlease wait"
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                delay(5000)
                                viewModel.checkAcc(id)
                            }
                        }
                    }
                }
            }


        }
    }

    private fun showErrorDialog(title: String, message: String) {
        dialog?.cancel()
        dialog = AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Ok") { dialog, _ -> dialog?.dismiss() }
        }.create()
        dialog?.show()
    }

    private fun goToNextPage() {
        Toast.makeText(this, "Login accepted!", Toast.LENGTH_SHORT).show()

        Intent(this, DashboardActivity::class.java).also {
            startActivity(it)
            finish()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)

        val sv = menu?.findItem(R.id.mySwitch)?.actionView?.findViewById<Button>(R.id.action_switch)
        sv?.setOnClickListener {
            mode = (mode+1)%3
            sv.text = apiMode[mode]
            Values.CC_API= mode
        }
        return true

    }

}



