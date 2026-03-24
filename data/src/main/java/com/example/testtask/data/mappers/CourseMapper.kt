package com.example.testtask.data.mappers

import com.example.testtask.data.remote.dto.CourseDto
import com.example.testtask.domain.Course

fun CourseDto.toDomain(): Course = Course(
    id = id,
    title = title,
    description = text,
    price = price,
    rate = rate,
    startDate = startDate,
    publishDate = publishDate,
    isFavorite = hasLike
)
