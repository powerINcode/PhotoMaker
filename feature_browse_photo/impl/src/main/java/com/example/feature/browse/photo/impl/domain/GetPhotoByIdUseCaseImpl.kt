package com.example.feature.browse.photo.impl.domain

import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class GetPhotoByIdUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
): GetPhotoByIdUseCase {
    override fun invoke(params: Long): Single<Photo> = photoRepository.getPhoto(params)
}