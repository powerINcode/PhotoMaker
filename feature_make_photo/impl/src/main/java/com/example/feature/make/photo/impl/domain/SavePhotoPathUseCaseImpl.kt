package com.example.feature.make.photo.impl.domain

import com.example.feature_make_photo.api.SavePhotoPathUseCase
import com.example.repositories.api.photo.PhotoRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class SavePhotoPathUseCaseImpl @Inject constructor(
    private val photoRepository: PhotoRepository
) : SavePhotoPathUseCase {

    override fun invoke(params: SavePhotoPathUseCase.Params): Completable {
        return photoRepository.savePhoto(name = params.name, path = params.path)
    }
}