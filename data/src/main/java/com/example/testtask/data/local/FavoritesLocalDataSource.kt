package com.example.testtask.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class FavoritesLocalDataSource(
    context: Context
) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val favoritesLock = Any()
    private val favorites: MutableSet<Int> = loadFavorites()

    fun getFavoriteIds(): Set<Int> =
        synchronized(favoritesLock) { favorites.toSet() }

    fun toggle(courseId: Int) {
        val idsToPersist: Set<String> = synchronized(favoritesLock) {
            if (favorites.contains(courseId)) {
                favorites.remove(courseId)
            } else {
                favorites.add(courseId)
            }
            favorites.map { it.toString() }.toSet()
        }

        prefs.edit {
            putStringSet(KEY_FAVORITES, idsToPersist)
        }
    }

    private fun loadFavorites(): MutableSet<Int> {
        val stored = prefs.getStringSet(KEY_FAVORITES, emptySet()).orEmpty()
        return stored.mapNotNull { it.toIntOrNull() }.toMutableSet()
    }

    private companion object {
        private const val PREFS_NAME = "courses_prefs"
        private const val KEY_FAVORITES = "favorite_course_ids"
    }
}
