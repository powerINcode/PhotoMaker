package com.example.feature.photo.gallery.api

import com.example.core.flow.FlowEntity
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase

interface PhotoGalleryApi: FlowEntity {
    fun getObservePhotosUseCase(): ObservePhotosUseCase
}