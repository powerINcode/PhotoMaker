package com.example.feature.browse.photo.impl.ui

internal interface BrowsePhotoContract {
    data class BrowsePhotoState(
        val photo: com.example.api.entities.Photo? = null
    ) {
        companion object {
            val EMPTY = BrowsePhotoState(
                photo = null
            )
        }
    }

    sealed class BrowsePhotoIntent {
        object ShowDummyFragment: BrowsePhotoIntent()
    }
}