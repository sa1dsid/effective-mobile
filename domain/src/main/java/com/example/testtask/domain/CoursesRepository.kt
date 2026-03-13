package com.example.testtask.domain

interface CoursesRepository {

    suspend fun getCourses(): List<Course>

    suspend fun getFavoriteCourses(): List<Course>

    suspend fun toggleFavorite(courseId: Int)
}