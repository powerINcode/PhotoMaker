package com.example.feature.photo.gallery.api.domain

import com.example.domain.SimpleFlowableUseCase

/**
 * Observe changes in the photo collection
 */
interface ObservePhotosUseCase  : SimpleFlowableUseCase<List<com.example.api.entities.Photo>>