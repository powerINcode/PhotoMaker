package com.example.feature.make.photo.impl.domain

import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class SavePhotoPathUseCaseImpl @Inject constructor(
    private val photoRepository: com.example.api.PhotoRepository
) : SavePhotoPathUseCase {

    override fun invoke(params: SavePhotoPathUseCase.Params): Completable {
        return photoRepository.savePhoto(name = params.name, path = params.path)
    }
}