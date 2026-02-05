package com.example.testtask.data.repository

import com.example.testtask.domain.Course
import com.example.testtask.domain.CoursesRepository
import com.example.testtask.data.mappers.toDomain
import com.example.testtask.data.network.CoursesApi

class CoursesRepositoryImpl(private val api: CoursesApi) : CoursesRepository {
    override suspend fun getCourses(): List<Course> =
        api.getCourses().courses.map { it.toDomain() }
}
