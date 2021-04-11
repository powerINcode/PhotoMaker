package com.example.feature.make.photo.impl.ui

import android.net.Uri
import androidx.annotation.VisibleForTesting
import com.example.ui.textview.TextDelegate

interface MakePhotoContract {
    data class MakePhotoState(
        val photoUri: Uri? = null,
        val loading: Boolean,
        val error: TextDelegate?
    ) {
        companion object {
            val EMPTY = MakePhotoState(
                photoUri = null,
                loading = false,
                error = null
            )
        }
    }

    sealed class MakePhotoIntent {
        object Back : MakePhotoIntent()
        object MakePhoto : MakePhotoIntent()
        object PhotoMade : MakePhotoIntent()
        data class SavePhoto(val name: String) : MakePhotoIntent()
    }

    companion object {
        @VisibleForTesting
        const val REQUEST_CODE_MAKE_PHOTO = 1000
    }
}