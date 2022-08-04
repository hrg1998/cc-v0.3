package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.*
import retrofit2.Response

class ApiCall {
    private val retrofitHandler by lazy {
        RetrofitHandler()
    }

    suspend fun createAccount(username: String,url:String):Response<CheckAccountResponseModel>{
        val apiDaoDev =retrofitHandler.apiDao as ApiDaoDev
        return apiDaoDev.createAccountForDev( CheckAccountInputModel(Account(username)),url)
    }

    suspend fun checkAccount(url:String): Response<CheckAccountResponseModel> {
        val apiDaoDev =retrofitHandler.apiDao as ApiDaoDev
        return apiDaoDev.checkAccountForDev(url)
    }

    suspend fun getScore(url: String):Response<ScoreResponseModel>{
        val apiDaoDev =retrofitHandler.apiDao as ApiDaoDev
        return apiDaoDev.getScore(url)
    }


}