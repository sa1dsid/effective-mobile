package com.example.testtask.domain

class GetFavoriteCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): List<Course> = repository.getFavoriteCourses()
}
