package com.example.feature.browse.photo.impl.ui

import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.repositories.api.photo.entities.Photo
import com.example.ui.viewmodel.StateReducer
import javax.inject.Inject

class BrowsePhotoReducer @Inject constructor(): StateReducer<BrowsePhotoState>(BrowsePhotoState.EMPTY) {
    fun setPhoto(photo: Photo) {
        state.copy(photo = photo).commit()
    }
}