package com.example.testtask.di

import com.example.testtask.feature_favorites.FavoriteViewModel
import com.example.testtask.feature_home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
}

val appModules = listOf(
    dataModule,
    domainModule,
    appModule
)
