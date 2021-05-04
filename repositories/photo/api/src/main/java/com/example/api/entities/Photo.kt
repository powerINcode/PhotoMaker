package com.example.api.entities

import com.example.api.PhotoRepository
import org.joda.time.Instant

/**
 * Entity describe photo information that stored in the [PhotoRepository]
 */
data class Photo(
    /**
     * Identifier of the record
     */
    val id: Long,
    /**
     * Name that user setup for the record
     */
    val name: String,
    /**
     * Path to the photo in the local storage
     */
    val path: String,
    /**
     * Date of the creation of the record
     */
    val createdAt: Instant
)