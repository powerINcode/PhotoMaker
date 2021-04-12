package com.example.feature.browse.photo.api.domain

import com.example.domain.SingleUseCase
import com.example.repositories.api.photo.entities.Photo

/**
 * Get photo by id
 */
interface GetPhotoByIdUseCase : SingleUseCase<Long, Photo>