package com.example.testtask

import android.os.Bundle
import android.util.Log
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
    fun onLoginSuccess() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        navGraph.setStartDestination(R.id.home_graph) // теперь старт — Home
        navController.graph = navGraph
    }
}

