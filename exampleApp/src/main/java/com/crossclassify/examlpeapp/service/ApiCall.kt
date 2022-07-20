package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.*
import com.google.gson.JsonObject
import retrofit2.Response

class ApiCall {
    private val retrofitHandler by lazy {
        RetrofitHandler()
    }

    suspend fun createAccountForDev(username: String):Response<CheckAccountResponseModelForDev>{
        return retrofitHandler.apiDao.createAccountForDev( CheckAccountInputModelForDev(Account(username)))
    }

    suspend fun checkAccountForDev(id: String): Response<CheckAccountResponseModelForDev> {
        return retrofitHandler.apiDao.checkAccountForDev(id)
    }

}