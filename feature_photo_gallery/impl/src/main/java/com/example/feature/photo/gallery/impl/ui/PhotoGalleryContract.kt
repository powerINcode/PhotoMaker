package com.example.feature.photo.gallery.impl.ui

import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.ui.textview.TextDelegate

interface PhotoGalleryContract {
    data class PhotoGalleryState(
        val photos: List<PhotoGalleryAdapter.Model>,
        val spanCount: Int,
        val photoSize: Int,
        val loading: Boolean,
        val error: TextDelegate?
    ) {
        companion object {
            val EMPTY = PhotoGalleryState(
                photos = emptyList(),
                spanCount = 1,
                photoSize = 0,
                loading = false,
                error = null
            )
        }
    }

    sealed class PhotoGalleryIntent {
        data class ContainerSizeChange(val containerSize: Int, val defaultItemSize: Int) : PhotoGalleryIntent()
        object MakePhoto : PhotoGalleryIntent()
        data class PhotoClick(val photoId: Long) : PhotoGalleryIntent()
    }
}