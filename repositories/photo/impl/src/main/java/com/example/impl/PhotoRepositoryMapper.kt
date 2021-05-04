package com.example.impl

import com.example.impl.database.entities.PhotoDbo
import org.joda.time.Instant
import javax.inject.Inject

internal class PhotoRepositoryMapper @Inject constructor() {

    fun map(dbo: PhotoDbo) = dbo.run {
        com.example.api.entities.Photo(
            id = id,
            name = name,
            path = path,
            createdAt = Instant(createdAt),
        )
    }
}