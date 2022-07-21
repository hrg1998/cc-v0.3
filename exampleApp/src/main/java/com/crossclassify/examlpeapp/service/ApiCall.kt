package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Url

class ApiCall {
    private val retrofitHandler by lazy {
        RetrofitHandler()
    }

    suspend fun createAccount(username: String,url:String):Response<CheckAccountResponseModelForDev>{
        val apiDaoDev =retrofitHandler.apiDao as ApiDaoDev
        return apiDaoDev.createAccountForDev( CheckAccountInputModelForDev(Account(username)),url)
    }

    suspend fun checkAccount(id: String, url:String): Response<CheckAccountResponseModelForDev> {
        val apiDaoDev =retrofitHandler.apiDao as ApiDaoDev
        return apiDaoDev.checkAccountForDev(id,url)
    }


}