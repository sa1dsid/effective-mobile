package com.example.testtask.domain

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
}