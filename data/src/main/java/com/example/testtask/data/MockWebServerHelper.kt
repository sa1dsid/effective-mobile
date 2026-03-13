package com.example.testtask.data

import android.content.Context
import android.util.Log
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.time.LocalDate

class MockWebServerHelper(private val context: Context) {
    val mockWebServer = MockWebServer()

    fun enqueueCourses() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(loadJson("courses.json"))
        )
    }

    fun loadJson(fileName: String): String =
        context.assets.open(fileName).bufferedReader().use { it.readText() }
}

