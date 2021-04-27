package com.example.feature.make.photo.impl.ui

import android.net.Uri
import com.example.feature.make.photo.impl.R
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.ui.textview.PureText
import com.example.ui.textview.ResourceText
import com.example.ui.viewmodel.statereducer.StateReducer
import javax.inject.Inject

class MakePhotoReducer @Inject constructor(): StateReducer<MakePhotoState>(MakePhotoState.EMPTY) {
    fun photoMade(photoUri: Uri) {
        commit { state ->
            state.copy(photoUri = photoUri)
        }
    }

    fun photoNameEmpty() {
        commit { state ->
            state.copy(
                error = ResourceText(R.string.make_photo_empty)
            )
        }
    }

    fun photoNotMade() {
        commit { state ->
            state.copy(
                error = ResourceText(R.string.make_photo_not_made)
            )
        }
    }

    fun startMakingPhoto() {
        commit { state ->
            state.copy(
                error = null
            )
        }
    }

    fun startSavePhoto() {
        commit { state ->
            state.copy(
                loading = true,
                error = null
            )
        }
    }

    fun createPhotoFileError(error: String?) {
        commit { state ->
            state.copy(
                loading = false,
                error = error?.let { PureText(it) }
            )
        }
    }

    fun photoNotSaved(error: String?) {
        commit { state ->
            state.copy(
                loading = false,
                error = error?.let { PureText(it) }
            )
        }
    }
}