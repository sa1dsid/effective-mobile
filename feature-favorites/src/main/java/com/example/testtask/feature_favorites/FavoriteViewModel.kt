package com.example.testtask.feature_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.Course
import com.example.testtask.domain.CoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: CoursesRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Course>>(emptyList())
    val favorites: StateFlow<List<Course>> = _favorites

    fun loadFavorites() {

        viewModelScope.launch {

            _favorites.value = repository.getFavoriteCourses()
        }
    }
    fun toggleFavorite(course: Course) {

        viewModelScope.launch {

            repository.toggleFavorite(course.id)

            _favorites.value = repository.getFavoriteCourses()
        }
    }
}