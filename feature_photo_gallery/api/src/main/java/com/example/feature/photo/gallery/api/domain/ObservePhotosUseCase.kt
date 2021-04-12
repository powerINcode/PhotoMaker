package com.example.feature.photo.gallery.api.domain

import com.example.domain.SimpleFlowableUseCase
import com.example.repositories.api.photo.entities.Photo

/**
 * Observe changes in the photo collection
 */
interface ObservePhotosUseCase  : SimpleFlowableUseCase<List<Photo>>