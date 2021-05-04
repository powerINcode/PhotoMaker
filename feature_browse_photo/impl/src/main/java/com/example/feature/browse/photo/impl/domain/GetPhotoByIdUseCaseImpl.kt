package com.example.feature.browse.photo.impl.domain

import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class GetPhotoByIdUseCaseImpl @Inject constructor(
    private val photoRepository: com.example.api.PhotoRepository
): GetPhotoByIdUseCase {
    override fun invoke(params: Long): Single<com.example.api.entities.Photo> = photoRepository.getPhoto(params)
}