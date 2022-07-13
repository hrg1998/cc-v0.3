package com.crossclassify.examlpeapp.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Utils {
    inline fun <reified T> String.toConvertStringJsonToModel(type: Class<T>): T {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter.fromJson(this)!!
    }
}