package com.example.testtask

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testtask.data.MockWebServerHelper
import com.example.testtask.data.local.FavoritesLocalDataSource
import com.example.testtask.data.network.CoursesApi
import com.example.testtask.data.repository.CoursesRepositoryImpl
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.domain.CoursesRepository
import com.example.testtask.domain.GetCoursesUseCase
import com.example.testtask.domain.GetFavoriteCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase
import com.example.testtask.feature_home.HomeViewModel
import com.example.testtask.feature_home.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.testtask.feature_favorites.FavoriteViewModel
import com.example.testtask.feature_favorites.FavoriteViewModelFactory

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

        initRepository()

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
    private fun initRepository() {
        lifecycleScope.launch(Dispatchers.IO) {

            mockServer = MockWebServer()
            mockServer.start()

            val json = MockWebServerHelper(this@MainActivity)
                .loadJson("courses.json")

            repeat(100) {
                mockServer.enqueue(
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
                )
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(mockServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(CoursesApi::class.java)

            val favoritesLocalDataSource = FavoritesLocalDataSource(applicationContext)
            repository = CoursesRepositoryImpl(api, favoritesLocalDataSource)

            withContext(Dispatchers.Main) {
                val getCoursesUseCase = GetCoursesUseCase(repository)
                val getFavoriteCoursesUseCase = GetFavoriteCoursesUseCase(repository)
                val toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)

                val homeFactory = HomeViewModelFactory(
                    getCoursesUseCase,
                    toggleFavoriteUseCase
                )

                ViewModelProvider(
                    this@MainActivity,
                    homeFactory
                )[HomeViewModel::class.java]

                val favoritesFactory = FavoriteViewModelFactory(
                    getFavoriteCoursesUseCase,
                    toggleFavoriteUseCase
                )

                ViewModelProvider(
                    this@MainActivity,
                    favoritesFactory
                )[FavoriteViewModel::class.java]

            }
        }
    }

}

