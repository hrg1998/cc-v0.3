package com.crossclassify.examlpeapp.simpleUsageExample

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.crossclassify.examlpeapp.MainViewModel
import com.crossclassify.examlpeapp.R
import com.crossclassify.examlpeapp.commonEpoxyExample.Epoxy2Activity
import com.crossclassify.examlpeapp.defaultEpoxyWithControllerExample.EpoxyActivity
import com.crossclassify.examlpeapp.defaultRecyclerViewExample.RecyclerActivity
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import com.crossclassify.examlpeapp.model.ScoreResponseModel
import com.crossclassify.trackersdk.ScreenNavigationTracking
import com.crossclassify.trackersdk.TrackerActivity
import com.crossclassify.trackersdk.model.FieldMetaData
import com.crossclassify.trackersdk.utils.objects.Values
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//extend from TrackerActivity if you have form in activity and need form content and behavior analysis
class SignUpActivity : TrackerActivity() {

    private var currentEmail: String = ""
    private var currentPassword: String = ""
    private var currentConfirmPass: String = ""
    private var currentUsername: String = ""
    private var lastEmail: String = ""

    private var maxRetry = 15

    private var cancel: Boolean = false


    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var id = ""
    private var dialog: Dialog? = null
    private var score: Int = 0
    private var loading = false
        set(value) {
            field = value
            when (value) {
                true -> layoutLoading.visibility = View.VISIBLE
                false -> layoutLoading.visibility = View.GONE
            }
        }

    //override getFormName and define a name for your form
    override fun getFormName(): String = "signup-form"

    //use it in case that you have recyclerView
    override fun getExternalMetaData(): List<FieldMetaData>? {
        return null
    }

    private fun fillAllFields(): MutableList<Any> {
        val myList = mutableListOf<Any>()
        when {
            editTextEmail.text.toString() == "" -> {
                myList.add("email")
                myList.add(false)
            }
            editTextUserName.text.toString() == "" -> {
                myList.add("username")
                myList.add(false)
            }
            editTextPassword.text.toString() == "" -> {
                myList.add("password")
                myList.add(false)
            }
            editTextConfirmPassword.text.toString() == "" -> {
                myList.add("confirm password")
                myList.add(false)
            }
            else -> {
                myList.add("all")
                myList.add(true)
            }
        }
        return myList
    }


    private fun setFields() {
        editTextEmail.setText(currentEmail)
        editTextUserName.setText(currentUsername)
        editTextPassword.setText(currentPassword)
        editTextConfirmPassword.setText(currentConfirmPass)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Values.CC_API = 1
        findViewById<LinearLayout>(R.id.btnsOptions).visibility = View.GONE
        //set onClickListener for your submit button and call trackerClickSubmitButton
        btnSubmit.setOnClickListener {
            Log.i("crossClassify:", Values.CC_API.toString())
            cancel = false
            maxRetry = 15
            lastEmail = currentEmail

            if (fillAllFields()[1] == true) {
                when {
                    editTextPassword.text.toString().length <= 4 -> {
                        showErrorDialog(
                            "Error",
                            "Your password must be at least 5 characters long."
                        )
                        loading = false
                    }
                    editTextPassword.text.toString() != editTextConfirmPassword.text.toString() -> {
                        showErrorDialog("Error", "Confirm password doesn't match.")
                        loading = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches() -> {
                        showErrorDialog("Incorrect Email", "Email address is incorrect.")
                        loading = false
                    }
                    else -> {
                        currentEmail = editTextEmail.text.toString()
                        currentUsername = editTextUserName.text.toString()
                        currentPassword = editTextPassword.text.toString()
                        currentConfirmPass = editTextConfirmPassword.text.toString()

                        viewModel.createAcc(currentEmail)
                        trackerClickSubmitButton()
                        setFields()
                        loading = true
                    }
                }
            } else {
                showErrorDialog("Error", "Please fill in all the fields.")
                loading = false
            }
        }

        goToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        var temp: CheckAccountResponseModel? = null
        viewModel.checkCreateAccountResult.observe(this) { result ->
            when (result) {
                is CheckAccountResponseModel -> {
                    if (result.status != null) {
                        when (result.status) {
                            "ready" -> {
                                temp = result
                                viewModel.getScore(currentEmail)
                            }
                            "call_for_calc" -> {
                                if (!cancel) {
                                    if ((dialog == null) or (dialog?.isShowing == false))
                                        showErrorDialogForRetry(
                                            "Please wait!",
                                            "Verifying account..."
                                        )
                                    if (maxRetry > 1) {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            delay(5000)
                                            viewModel.checkAcc(id)
                                            maxRetry--
                                        }
                                    } else {

                                        dialog?.dismiss()
                                        showErrorDialog("Error!", "The system is not available!")
                                        loading = false
                                    }
                                }
                            }
                        }
                    } else {
                        id = result._id
                        viewModel.checkAcc(result._id)
                    }
                }
                is Int -> {
                    loading = if (result == 403) {
                        showErrorDialog("Error", "Please connect with VPN")
                        loading = false
                        setFields()
                        false
                    } else {
                        showErrorDialog("Unknown Response!", "Please try again...")
                        loading = false
                        setFields()
                        false
                    }
                }
                is String -> {

                    showErrorDialog("Request Time Out!", "Please check your connection...")
                    loading = false
                    setFields()
                }
            }

        }

        viewModel.checkAccountResult.observe(this) { result ->
            when (result) {
                is CheckAccountResponseModel -> {
                    when (result.status) {
                        "ready" -> {
                            temp = result
                            viewModel.getScore(email = currentEmail)


                        }
                        "call_for_calc" -> {
                            if (!cancel) {
                                if ((dialog == null) or (dialog?.isShowing == false))
                                    showErrorDialogForRetry(
                                        "Please wait!",
                                        "Verifying account..."
                                    )
                                if (maxRetry > 1) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        delay(5000)
                                        viewModel.checkAcc(id)
                                        maxRetry--
                                    }
                                } else {
                                    dialog?.dismiss()

                                    showErrorDialog("Error", "The system is not available!")
                                    loading = false
                                }

                            }
                            setFields()
                        }
                    }
                }
                is String -> {
                    showErrorDialog("Request Time Out!", "Please check your connection...")
                    loading = false
                    setFields()
                }
            }


        }

        viewModel.scoreResult.observe(this) { result ->
            when (result) {
                is ScoreResponseModel -> {
                    for (item in result.Items) {
                        score = item.automaticDecisionDetail?.score ?: 0
                        break
                    }
                    when (temp?.isBlocked) {
                        false -> {
                            if (!cancel) {
                                showErrorDialog(
                                    "Welcome",
                                    "Welcome to our community \n Your score is $score. ",
                                    true
                                )
                            }

                        }
                        true -> {
                            if (!cancel) {
                                showErrorDialog(
                                    "Blocked",
                                    "You can't open this account \n Your score is $score "
                                )
                                loading = false
                                setFields()
                            }

                        }
                    }
                    loading = false
                }
            }
        }
    }

    private fun showErrorDialog(title: String, message: String, isWelcome:Boolean=false) {
        dialog?.cancel()
        dialog = Dialog(this)

        dialog!!.setContentView(R.layout.custom_dialog)

        dialog!!.progress_bar.visibility = View.GONE


        dialog!!.txt_title.text = title
        dialog!!.txt_massage.text = message
        dialog!!.txt_cancel.text = "ok"


        dialog!!.txt_cancel.setOnClickListener {
            dialog?.dismiss()

            if (isWelcome){
                goToNextPage()
            }

        }

        dialog?.show()
    }


    private fun showErrorDialogForRetry(title: String, message: String) {
        dialog?.cancel()
        cancel = false
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.custom_dialog)
        dialog!!.progress_bar.visibility = View.VISIBLE
        dialog!!.txt_title.text = title
        dialog!!.txt_massage.text = message
        dialog!!.txt_cancel.text = "cancel"
        dialog!!.txt_cancel.setOnClickListener {
            dialog?.dismiss()
            loading = false
            cancel = true
            maxRetry = -1
        }

        dialog?.show()

    }

    private fun goToNextPage() {
        Toast.makeText(this, "Login accepted!", Toast.LENGTH_SHORT).show()

        Intent(this, DashboardActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        //screen navigation tracking
        //pass activity path and title to trackNavigation method for screen navigation purposes
        ScreenNavigationTracking().trackNavigation("/activity_splash/activity_signup", "signup")
    }


}



