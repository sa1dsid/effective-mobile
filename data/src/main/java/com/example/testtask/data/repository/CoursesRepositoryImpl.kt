package com.example.testtask.data.repository

import com.example.testtask.domain.Course
import com.example.testtask.domain.CoursesRepository
import com.example.testtask.data.mappers.toDomain
import com.example.testtask.data.network.CoursesApi

class CoursesRepositoryImpl(
    private val api: CoursesApi
) : CoursesRepository {

    private val favorites = mutableSetOf<Int>()

    override suspend fun getCourses(): List<Course> {
        return api.getCourses().courses.map {

            val course = it.toDomain()

            course.copy(
                isFavorite = favorites.contains(course.id)
            )
        }
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        return getCourses().filter { it.isFavorite }
    }

    override suspend fun toggleFavorite(courseId: Int) {

        if (favorites.contains(courseId)) {
            favorites.remove(courseId)
        } else {
            favorites.add(courseId)
        }
    }
}
