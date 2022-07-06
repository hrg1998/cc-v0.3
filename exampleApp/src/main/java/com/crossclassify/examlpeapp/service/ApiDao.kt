package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// TODO: Check url
interface ApiDao {
    @POST("fraudServices/openning/makeFormDecisions")
    suspend fun createAccount(@Body checkAccountInputModel: CheckAccountInputModel): Response<CheckAccountResponseModel>

    @GET("fraudServices/openning/makeFormDecisions/{id}")
    suspend fun checkAccount(@Path("id") id: String): Response<CheckAccountResponseModel>
}