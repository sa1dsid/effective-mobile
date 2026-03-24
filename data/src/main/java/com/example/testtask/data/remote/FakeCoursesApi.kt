package com.example.testtask.data.remote

import android.content.Context
import com.example.testtask.data.remote.dto.CoursesResponseDto
import com.google.gson.Gson

class FakeCoursesApi(
    private val context: Context,
    private val gson: Gson
) : CoursesApi {

    override suspend fun getCourses(): CoursesResponseDto {
        val json = context.assets.open("courses.json")
            .bufferedReader()
            .use { it.readText() }

        return gson.fromJson(json, CoursesResponseDto::class.java)
    }
}
