package com.example.testtask.feature_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testtask.domain.CoursesRepository

class FavoriteViewModelFactory(
    private val repository: CoursesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}