package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.ScoreResponseModel
import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import com.crossclassify.examlpeapp.model.CheckAccountResponseModel
import retrofit2.Response
import retrofit2.http.*

// TODO: Check url
interface ApiDaoDev {

    @POST
    suspend fun createAccountForDev(@Body checkAccountInputModel: CheckAccountInputModel, @Url url:String): Response<CheckAccountResponseModel>

    @GET
    suspend fun checkAccountForDev(@Url url:String): Response<CheckAccountResponseModel>

    @GET
    suspend fun getScore(@Url url: String):Response<ScoreResponseModel>
}