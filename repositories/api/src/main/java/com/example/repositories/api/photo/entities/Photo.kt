package com.example.repositories.api.photo.entities

import org.joda.time.Instant

data class Photo(
    val id: Long,
    val name: String,
    val path: String,
    val createdAt: Instant
)