package com.example.testtask.di

import com.example.testtask.domain.GetCoursesUseCase
import com.example.testtask.domain.GetFavoriteCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCoursesUseCase(get()) }
    factory { GetFavoriteCoursesUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
}
