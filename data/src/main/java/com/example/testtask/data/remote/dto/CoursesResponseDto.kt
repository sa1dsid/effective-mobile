package com.example.testtask.data.remote.dto

data class CoursesResponseDto(
    val courses: List<CourseDto>
)
data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)
