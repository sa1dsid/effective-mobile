package com.example.testtask.di

import com.example.testtask.data.local.FavoritesLocalDataSource
import com.example.testtask.data.repository.CoursesRepositoryImpl
import com.example.testtask.data.remote.CoursesApi
import com.example.testtask.data.remote.FakeCoursesApi
import com.example.testtask.domain.CoursesRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single { FavoritesLocalDataSource(androidContext()) }
    single { Gson() }
    single<CoursesApi> { FakeCoursesApi(androidContext(), get()) }

    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }
}
