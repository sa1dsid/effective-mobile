package com.example.testtask.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.domain.GetCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase

class HomeViewModelFactory(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getCoursesUseCase, toggleFavoriteUseCase) as T
    }
}
