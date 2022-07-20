package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModelForDev
import com.crossclassify.examlpeapp.model.CheckAccountResponseModelForDev
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// TODO: Check url
interface ApiDao {

    @POST("fraudServices/opening/makeFormDecision")
    suspend fun createAccountForDev(@Body checkAccountInputModelForDev: CheckAccountInputModelForDev): Response<CheckAccountResponseModelForDev>

    @GET("fraudServices/opening/makeFormDecision/{id}")
    suspend fun checkAccountForDev(@Path("id") id: String): Response<CheckAccountResponseModelForDev>
}