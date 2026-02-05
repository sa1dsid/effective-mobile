package com.example.testtask.data.network

import retrofit2.http.GET

interface CoursesApi {
    @GET("courses")
    suspend fun getCourses(): CoursesResponseDto
}