package com.example.feature.photo.gallery.impl.ui

import androidx.annotation.VisibleForTesting
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.PhotoGalleryState
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.repositories.api.photo.entities.Photo
import com.example.ui.viewmodel.StateReducer
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject

class PhotoGalleryReducer @Inject constructor() : StateReducer<PhotoGalleryState>(PhotoGalleryState.EMPTY) {
    fun setPhotos(photos: List<Photo>) {
        state.copy(photos = photos.map { photo ->
            PhotoGalleryAdapter.Model(
                id = photo.id.toString(),
                photoId = photo.id,
                name = photo.name,
                date = DATE_FORMATTER.print(LocalDate(photo.createdAt.millis)),
                path = photo.path
            )
        }).commit()
    }

    fun containerSizeChange(spanCount: Int, itemSize: Int) {
        state.copy(
            spanCount = spanCount,
            photoSize = itemSize
        ).commit()
    }

    companion object {
        @VisibleForTesting
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormat.forPattern("dd MMMM, yyyy")
    }
}