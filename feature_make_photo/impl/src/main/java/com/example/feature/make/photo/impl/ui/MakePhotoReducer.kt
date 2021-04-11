package com.example.feature.make.photo.impl.ui

import android.net.Uri
import com.example.feature.make.photo.impl.R
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.ui.textview.PureText
import com.example.ui.textview.ResourceText
import com.example.ui.viewmodel.StateReducer
import javax.inject.Inject

class MakePhotoReducer @Inject constructor(): StateReducer<MakePhotoState>(MakePhotoState.EMPTY) {
    fun photoMade(photoUri: Uri) {
        state.copy(photoUri = photoUri).commit()
    }

    fun photoNameEmpty() {
        state.copy(
            error = ResourceText(R.string.make_photo_empty)
        ).commit()
    }

    fun startSavePhoto() {
        state.copy(
            loading = true,
            error = null
        ).commit()
    }

    fun createPhotoFileError(error: String?) {
        state.copy(
            loading = false,
            error = error?.let { PureText(it) }
        ).commit()
    }

    fun photoNotSaved(error: String?) {
        state.copy(
            loading = false,
            error = error?.let { PureText(it) }
        ).commit()
    }
}