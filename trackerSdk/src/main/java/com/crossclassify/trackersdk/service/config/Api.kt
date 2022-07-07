package com.crossclassify.trackersdk.service.config

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.crossclassify.trackersdk.interfaces.api.ApiInterface
import com.crossclassify.trackersdk.utils.objects.Values
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Api {
    private var baseUrl = "https://matomo-cc-dev-dinl5i5e5a-ts.a.run.app/"
    fun client(context: Context): ApiInterface {
        when(Values.CC_API){
            0 ->{
                baseUrl = "https://matomo-cc-dev-dinl5i5e5a-ts.a.run.app/"
            }
            1->{
                baseUrl ="https://matomo-cc-prod-dinl5i5e5a-ts.a.run.app/"
            }
            2->{
                baseUrl ="https://matomo-cc-stg-dinl5i5e5a-ts.a.run.app/"
            }
        }
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(
            ChuckerInterceptor.Builder(context)
                .build()
        )
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}