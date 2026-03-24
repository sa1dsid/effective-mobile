package com.example.testtask.data.remote

import com.example.testtask.data.remote.dto.CoursesResponseDto
import retrofit2.http.GET

interface CoursesApi {
    @GET("courses")
    suspend fun getCourses(): CoursesResponseDto
}
