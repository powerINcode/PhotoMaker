package com.example.feature.photo.gallery.impl.ui

import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.ui.textview.TextDelegate
import com.example.ui.viewmodel.statereducer.StatePayload

interface PhotoGalleryContract {
    data class PhotoGalleryState(
        val photos: List<PhotoGalleryAdapter.Model>,
        val spanCount: Int,
        val photoSize: Int,
        val loading: Boolean,
        val error: TextDelegate?
    ) : StatePayload<PhotoGalleryState, PhotoGalleryState.Payload> {
        companion object {
            val EMPTY = PhotoGalleryState(
                photos = emptyList(),
                spanCount = 1,
                photoSize = 0,
                loading = false,
                error = null
            )
        }

        data class Payload(
            val photosChanged: Boolean,
            val spanCountChanged: Boolean,
            val photoSizeChanged: Boolean
        )

        override fun get(payload: Any?): Payload {
            return (payload as? Payload) ?: empty
        }

        override fun calculate(oldState: PhotoGalleryState): Any {
            return Payload(
                photosChanged = photos != oldState.photos,
                spanCountChanged = spanCount != oldState.spanCount,
                photoSizeChanged = photoSize != oldState.photoSize
            )
        }

        override val empty: Payload = Payload(
            photosChanged = true,
            spanCountChanged = true,
            photoSizeChanged = true
        )
    }

    sealed class PhotoGalleryIntent {
        data class ContainerSizeChange(val containerSize: Int, val defaultItemSize: Int) : PhotoGalleryIntent()
        object MakePhoto : PhotoGalleryIntent()
        data class PhotoClick(val photoId: Long) : PhotoGalleryIntent()
    }
}