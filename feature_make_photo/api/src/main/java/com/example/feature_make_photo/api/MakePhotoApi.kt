package com.example.feature_make_photo.api

import com.example.core.flow.FlowEntity

interface MakePhotoApi: FlowEntity {
    fun getSavePhotoUseCase(): SavePhotoPathUseCase
}