package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModelForDev
import com.crossclassify.examlpeapp.model.CheckAccountResponseModelForDev
import retrofit2.Response
import retrofit2.http.*

// TODO: Check url
interface ApiDaoDev {

    @POST
    suspend fun createAccountForDev(@Body checkAccountInputModelForDev: CheckAccountInputModelForDev,@Url url:String): Response<CheckAccountResponseModelForDev>

    @GET
    suspend fun checkAccountForDev(@Url url:String): Response<CheckAccountResponseModelForDev>
}