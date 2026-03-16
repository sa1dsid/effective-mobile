package com.example.testtask.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.testtask.domain.Course
import com.example.testtask.domain.CoursesRepository
import com.example.testtask.data.mappers.toDomain
import com.example.testtask.data.network.CoursesApi

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    context: Context
) : CoursesRepository {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val favoritesLock = Any()
    private val favorites: MutableSet<Int> = loadFavoritesFromPrefs()

    override suspend fun getCourses(): List<Course> {
        val favoriteIds = synchronized(favoritesLock) { favorites.toSet() }
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

        val idsToPersist: Set<String> = synchronized(favoritesLock) {
            if (favorites.contains(courseId)) {
                favorites.remove(courseId)
            } else {
                favorites.add(courseId)
            }
            favorites.map { it.toString() }.toSet()
        }

        prefs.edit()
            .putStringSet(KEY_FAVORITES, idsToPersist)
            .apply()
    }

    private fun loadFavoritesFromPrefs(): MutableSet<Int> {
        val stored = prefs.getStringSet(KEY_FAVORITES, emptySet()).orEmpty()
        return stored.mapNotNull { it.toIntOrNull() }.toMutableSet()
    }

    private companion object {
        private const val PREFS_NAME = "courses_prefs"
        private const val KEY_FAVORITES = "favorite_course_ids"
    }
}
