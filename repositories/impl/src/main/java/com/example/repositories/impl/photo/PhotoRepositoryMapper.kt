package com.example.repositories.impl.photo

import com.example.repositories.api.photo.entities.Photo
import com.example.repositories.impl.database.entities.PhotoDbo
import org.joda.time.Instant
import javax.inject.Inject

internal class PhotoRepositoryMapper @Inject constructor() {

    fun map(dbo: PhotoDbo) = dbo.run {
        Photo(
            id = id,
            name = name,
            path = path,
            createdAt = Instant(createdAt),
        )
    }
}