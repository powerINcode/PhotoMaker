package com.example.feature.browse.photo.api.domain

import com.example.domain.SingleUseCase

/**
 * Get photo by id
 */
interface GetPhotoByIdUseCase : SingleUseCase<Long, com.example.api.entities.Photo>