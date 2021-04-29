package com.example.feature.browse.photo.impl.ui.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface DummyContract {
    data class DummyState(
        val phrase: String? = null
    ) {
        companion object {
            val EMPTY = DummyState(
                phrase = null
            )
        }
    }

    @Parcelize
    data class Configuration(val phrase: String): Parcelable
}