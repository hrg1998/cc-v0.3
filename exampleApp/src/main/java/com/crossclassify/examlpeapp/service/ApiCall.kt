package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import com.google.gson.JsonObject
import retrofit2.Response

class ApiCall {
    private val retrofitHandler by lazy {
        RetrofitHandler()
    }

    suspend fun createAccount(username: String): Response<CheckAccountResponseModel> {
        return retrofitHandler.apiDao.createAccount(CheckAccountInputModel(username))
    }

    suspend fun checkAccount(id: String): Response<CheckAccountResponseModel> {
        return retrofitHandler.apiDao.checkAccount(id)
    }
}