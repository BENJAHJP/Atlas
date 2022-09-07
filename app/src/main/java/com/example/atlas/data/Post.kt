package com.example.atlas.data

data class Post(
    val id: Int? = null,
    val title: String,
    val description: String,
    val created_at: String? = null,
    val updated_at: String? = null
)
