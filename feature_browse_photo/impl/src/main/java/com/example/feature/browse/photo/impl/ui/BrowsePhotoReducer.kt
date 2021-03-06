package com.example.feature.browse.photo.impl.ui

import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.ui.viewmodel.statereducer.StateReducer
import javax.inject.Inject

internal class BrowsePhotoReducer @Inject constructor(): StateReducer<BrowsePhotoState>(BrowsePhotoState.EMPTY) {
    fun setPhoto(photo: com.example.api.entities.Photo)  {
        commit { state ->
            state.copy(photo = photo)
        }
    }
}