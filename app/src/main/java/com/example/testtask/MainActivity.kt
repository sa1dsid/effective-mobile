package com.example.testtask

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testtask.data.MockWebServerHelper
import com.example.testtask.data.network.CoursesApi
import com.example.testtask.data.repository.CoursesRepositoryImpl
import com.example.testtask.domain.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var repository: CoursesRepository
    private lateinit var mockServer: MockWebServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO) {
            mockServer = MockWebServer()
            mockServer.start()

            val json = MockWebServerHelper(this@MainActivity).loadJson("courses.json")

            mockServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(json)
            )

            val retrofit = Retrofit.Builder()
                .baseUrl(mockServer.url("/")) // <-- localhost
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(CoursesApi::class.java)
            repository = CoursesRepositoryImpl(api)

            try {
                val courses = repository.getCourses()
                withContext(Dispatchers.Main) {
                    courses.forEach {
                        Log.d("TestCourses", "${it.title} | Favorite: ${it.isFavorite}")
                    }
                }
            } catch (e: Exception) {
                Log.e("TestCourses", "Ошибка при получении курсов", e)
            } finally {
                mockServer.shutdown()
            }
        }
    }
}

