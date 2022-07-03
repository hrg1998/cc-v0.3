package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import retrofit2.Response

class ApiCall {
    private val retrofitHandler by lazy {
        RetrofitHandler()
    }

    suspend fun checkAccount(username: String): Response<String> {
        return retrofitHandler.apiDao.checkAccount(CheckAccountInputModel(username))
    }
}