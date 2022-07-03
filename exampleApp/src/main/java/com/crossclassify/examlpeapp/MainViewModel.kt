package com.crossclassify.examlpeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.crossclassify.examlpeapp.service.ApiCall
import com.crossclassify.examlpeapp.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiCall = ApiCall()

    private val _checkAccountResult = SingleLiveEvent<Boolean>()
    val checkAccountResult: LiveData<Boolean>
        get() = _checkAccountResult

    fun checkAcc(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCall.checkAccount(username)
            if (response.isSuccessful) {
                // check response
            } else {
                // error
            }
        }
    }
}