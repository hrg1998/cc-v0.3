package com.crossclassify.examlpeapp.simpleUsageExample

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crossclassify.examlpeapp.R
import com.crossclassify.trackersdk.ScreenNavigationTracking
import com.crossclassify.trackersdk.TrackerFragment
import com.crossclassify.trackersdk.model.FieldMetaData
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.editTextEmail
import kotlinx.android.synthetic.main.fragment_login.editTextPassword

//extend from TrackerFragment if you have form and need form content and behavior analysis in fragment
//override setFormName and define a name for your form
class LoginFragment : TrackerFragment() {
    private var currentEmail: String = ""
    private var currentPassword: String = ""
    private var dialog: Dialog? = null
    override fun getFormName(): String = "login-form"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    private fun fillAllFields(): MutableList<Any> {
        val myList = mutableListOf<Any>()
        when {
            editTextEmail.text.toString() == "" -> {
                myList.add("email")
                myList.add(false)
            }
            editTextPassword.text.toString() == "" -> {
                myList.add("password")
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
        editTextPassword.setText(currentPassword)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set onClickListener for your submit button and call trackerClickSubmitButton()
        btnSubmitFragment.setOnClickListener {
            if (fillAllFields()[1] == true) {
                    currentEmail = editTextEmail.text.toString()
                    currentPassword = editTextPassword.text.toString()
                    trackerClickSubmitButton()

                showErrorDialog("Error", "Account takeover is not available.")
            } else {
                showErrorDialog("Error", "Please fill in all the fields.")
            }
        }

        goToRegister.setOnClickListener {
            val intent = Intent(requireContext(),SignUpActivity::class.java)
            startActivity(intent)

        }
    }
    private fun showErrorDialog(title: String, message: String) {
        dialog?.cancel()
        dialog = Dialog(requireContext())
        dialog!!.setContentView(R.layout.custom_dialog)
        dialog!!.txt_title.text = title
        dialog!!.txt_massage.text= message
        dialog!!.txt_cancel.text="ok"
        dialog!!.progress_bar.visibility=View.GONE
        dialog!!.txt_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.show()
    }
    override fun getExternalMetaData(): List<FieldMetaData>? {
        return null
    }

    override fun onResume() {
        super.onResume()
        //screen navigation tracking
        //pass fragment path and title to trackNavigation method for screen navigation purposes
        ScreenNavigationTracking().trackNavigation(
            "/activity_login/activity_signup/fragment_signup",
            "SignUp"
        )
    }
}