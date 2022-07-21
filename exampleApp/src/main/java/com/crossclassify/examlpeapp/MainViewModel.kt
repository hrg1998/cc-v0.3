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

class MainViewModel : ViewModel() {
    private val apiCall = ApiCall()

    private val _checkCreateAccountResult = SingleLiveEvent<Any?>()
    val checkCreateAccountResult: LiveData<Any?>
        get() = _checkCreateAccountResult

    private val _checkAccountResult = SingleLiveEvent<Any?>()
    val checkAccountResult: LiveData<Any?>
        get() = _checkAccountResult

    var baseUrl =""

    fun createAcc(username: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val response =
                try {
                    when(Values.CC_API){
                        0 ->{
                            apiCall.createAccount(username,"https://eve-dev-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision")
                        }
                        1->{
                            apiCall.createAccount(username,"https://eve-prod-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision")
                        }
                        2 ->{
                            apiCall.createAccount(username,"https://eve-stg-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision")
                        }
                        else ->{
                            null
                        }
                    }

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
                    when(Values.CC_API){
                        0 ->{
                            apiCall.checkAccount(id,"https://eve-dev-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision/{id}")
                        }
                        1->{
                            apiCall.checkAccount(id,"https://eve-prod-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision/{id}")
                        }
                        2 ->{
                            apiCall.checkAccount(id,"https://eve-stg-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/" +
                                    "fraudServices/opening/makeFormDecision/{id}")
                        }
                        else ->{
                            null
                        }
                    }
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