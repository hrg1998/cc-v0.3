package com.crossclassify.examlpeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import com.crossclassify.examlpeapp.service.ApiCall
import com.crossclassify.examlpeapp.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiCall = ApiCall()

    private val _checkCreateAccountResult = SingleLiveEvent<Any?>()
    val checkCreateAccountResult: LiveData<Any?>
        get() = _checkCreateAccountResult

    private val _checkAccountResult = SingleLiveEvent<Any?>()
    val checkAccountResult: LiveData<Any?>
        get() = _checkAccountResult

    fun createAcc(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCall.createAccount(username)
            _checkCreateAccountResult.postValue(
                if (response.isSuccessful) {
                    // check response
                    response.body()
                } else {
                    response.code()
                }
            )
        }
    }

    fun checkAcc(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCall.checkAccount(id)
            _checkAccountResult.postValue(
                if (response.isSuccessful) {
                    // check response
                    response.body()
                } else {
                    response.code()
                }
            )
        }
    }
}