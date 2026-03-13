package com.example.testtask.domain

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean
)