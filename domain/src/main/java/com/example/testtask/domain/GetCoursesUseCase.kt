package com.example.testtask.domain

class GetCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): List<Course> = repository.getCourses()
}