package com.example.testtask.feature_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.domain.GetFavoriteCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase

class FavoriteViewModelFactory(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(getFavoriteCoursesUseCase, toggleFavoriteUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}
