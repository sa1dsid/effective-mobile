package com.example.testtask.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Читаем JSON из assets
        val json = context.assets.open("courses.json")
            .bufferedReader()
            .use { it.readText() }

        // Создаём ответ
        return chain.proceed(request)
            .newBuilder()
            .code(200)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .message(json)
            .body(json.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))
            .build()
    }
}
