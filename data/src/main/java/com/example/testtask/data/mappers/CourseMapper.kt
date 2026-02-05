package com.example.testtask.data.mappers

import com.example.testtask.domain.Course
import com.example.testtask.data.network.CourseDto

fun CourseDto.toDomain(): Course = Course(
    id = id,
    title = title,
    description = text,
    price = price,
    rate = rate.toDouble(),
    startDate = startDate,
    publishDate = publishDate,
    isFavorite = hasLike
)
