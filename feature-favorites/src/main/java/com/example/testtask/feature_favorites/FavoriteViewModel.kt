package com.example.testtask.feature_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.Course
import com.example.testtask.domain.GetFavoriteCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Course>>(emptyList())
    val favorites: StateFlow<List<Course>> = _favorites

    fun loadFavorites() {

        viewModelScope.launch {

            _favorites.value = getFavoriteCoursesUseCase()
        }
    }
    fun toggleFavorite(course: Course) {

        viewModelScope.launch {

            toggleFavoriteUseCase(course.id)

            _favorites.value = getFavoriteCoursesUseCase()
        }
    }
}
