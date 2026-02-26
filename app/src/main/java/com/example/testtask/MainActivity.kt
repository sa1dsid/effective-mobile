package com.example.testtask

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testtask.data.MockWebServerHelper
import com.example.testtask.data.network.CoursesApi
import com.example.testtask.data.repository.CoursesRepositoryImpl
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.domain.CoursesRepository
import com.example.testtask.feature_home.HomeFragment
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
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBottomNavigation()

        loadCoursesWithMockServer()

    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.example.testtaskfeature_login.R.id.loginFragment -> binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun loadCoursesWithMockServer() {
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

