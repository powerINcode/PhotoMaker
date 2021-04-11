package com.example.feature_make_photo.api.domain

import com.example.domain.CompletableUseCase

interface SavePhotoPathUseCase  : CompletableUseCase<SavePhotoPathUseCase.Params> {
    data class Params(
        val name: String,
        val path: String
    )
}