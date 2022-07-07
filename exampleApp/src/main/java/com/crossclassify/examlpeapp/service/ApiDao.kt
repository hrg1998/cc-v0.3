package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import com.crossclassify.examlpeapp.model.CheckAccountInputModelForDev
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModelForDev
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// TODO: Check url
interface ApiDao {
    @POST("fraudServices/openning/makeFormDecisions")
    suspend fun createAccount(@Body checkAccountInputModel: CheckAccountInputModel): Response<CheckAccountResponseModel>

    @POST("fraudServices/takeover/makeFormDecision")
    suspend fun createAccountForDev(@Body checkAccountInputModelForDev: CheckAccountInputModelForDev): Response<CheckAccountResponseModelForDev>

    @GET("fraudServices/openning/makeFormDecisions/{id}")
    suspend fun checkAccount(@Path("id") id: String): Response<CheckAccountResponseModel>

    @GET("fraudServices/takeover/makeFormDecision/{id}")
    suspend fun checkAccountForDev(@Path("id") id: String): Response<CheckAccountResponseModelForDev>
}