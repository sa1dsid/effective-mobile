package com.example.testtask.data.repository

import com.example.testtask.data.local.FavoritesLocalDataSource
import com.example.testtask.data.mappers.toDomain
import com.example.testtask.data.remote.CoursesApi
import com.example.testtask.domain.Course
import com.example.testtask.domain.CoursesRepository

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val favoritesLocalDataSource: FavoritesLocalDataSource
) : CoursesRepository {

    override suspend fun getCourses(): List<Course> {
        val favoriteIds = favoritesLocalDataSource.getFavoriteIds()
        return api.getCourses().courses.map {

            val course = it.toDomain()

            course.copy(
                isFavorite = favoriteIds.contains(course.id)
            )
        }
    }

    override suspend fun getFavoriteCourses(): List<Course> {
        return getCourses().filter { it.isFavorite }
    }

    override suspend fun toggleFavorite(courseId: Int) {
        favoritesLocalDataSource.toggle(courseId)
    }
}
