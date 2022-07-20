package com.crossclassify.examlpeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModelForDev
import com.crossclassify.examlpeapp.service.ApiCall
import com.crossclassify.examlpeapp.util.SingleLiveEvent
import com.crossclassify.examlpeapp.util.Utils.toConvertStringJsonToModel
import com.crossclassify.trackersdk.utils.objects.Values
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeoutException

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

            val response =
                try {
                    apiCall.createAccountForDev(username)
                } catch (e: Exception) {
                    null
                }

            _checkCreateAccountResult.postValue(
                if (response != null) {
                    if (response.isSuccessful) {
                        // check response
                        response.body()
                    } else {
                        if (response.code() == 409)
                            response.errorBody()?.string()
                                ?.toConvertStringJsonToModel(CheckAccountResponseModelForDev::class.java)
                        else response.code()
                    }

                } else {
                    "timeOut"
                }
            )
        }
    }

    fun checkAcc(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                try {
                            apiCall.checkAccountForDev(id)



                } catch (e: Exception) {
                    null
                }

            _checkAccountResult.postValue(
                if (response != null) {
                    if (response.isSuccessful) {
                        // check response
                        response.body()
                    } else {
                        if (response.code() == 409)
                            response.errorBody()?.string()
                                ?.toConvertStringJsonToModel(CheckAccountResponseModelForDev::class.java)
                        else response.code()
                    }

                } else {
                    "timeOut"
                }
            )
        }
    }
}