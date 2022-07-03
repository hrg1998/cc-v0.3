package com.crossclassify.examlpeapp.service

import com.crossclassify.examlpeapp.model.CheckAccountInputModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
// TODO: Check url
interface ApiDao {
    @POST("T036J7CR7L0/B03HZH56TNW/ddtKi13wsdQ2ciJ5sxeqrU8i")
    suspend fun checkAccount(@Body checkAccountInputModel: CheckAccountInputModel): Response<String>
}