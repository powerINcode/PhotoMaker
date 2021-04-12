package com.example.feature.browse.photo.impl.ui

import com.example.repositories.api.photo.entities.Photo

interface BrowsePhotoContract {
    data class BrowsePhotoState(
        val photo: Photo? = null
    ) {
        companion object {
            val EMPTY = BrowsePhotoState(
                photo = null
            )
        }
    }
}