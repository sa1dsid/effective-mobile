package com.example.testtask.domain

class ToggleFavoriteUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(courseId: Int) = repository.toggleFavorite(courseId)
}
