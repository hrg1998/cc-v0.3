package com.crossclassify.examlpeapp.service

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHandler {
    val apiDao: Any by lazy {

        devRetrofit.create(ApiDaoDev::class.java)
    }


    private val devRetrofit by lazy<Retrofit> {
        val baseUrl = "https://eve-dev-dinl5i5e5a-ts.a.run.app/projects/62274ab07881f1715a512db6/"
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient by lazy<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return@lazy OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val interceptor by lazy {
        Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder
                .addHeader("Content-type", "application/json")
            chain.proceed(builder.build())
        }
    }
}
