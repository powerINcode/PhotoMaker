package com.example.feature_make_photo.api

import com.example.core.flow.FlowEntity
import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase

interface MakePhotoApi: FlowEntity {
    fun getSavePhotoUseCase(): SavePhotoPathUseCase
}