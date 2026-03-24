package com.example.testtask.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.Course
import com.example.testtask.domain.GetCoursesUseCase
import com.example.testtask.domain.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    fun loadCourses() {
        viewModelScope.launch {
            _courses.value = getCoursesUseCase()
        }
    }
    fun toggleFavorite(course: Course) {

        viewModelScope.launch {

            toggleFavoriteUseCase(course.id)

            _courses.value = getCoursesUseCase()
        }
    }
}
