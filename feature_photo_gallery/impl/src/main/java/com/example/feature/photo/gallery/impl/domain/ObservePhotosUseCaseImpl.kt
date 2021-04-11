package com.example.feature.photo.gallery.impl.domain

import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

internal class ObservePhotosUseCaseImpl @Inject constructor(
    private val repository: PhotoRepository
): ObservePhotosUseCase {

    override fun invoke(params: Unit): Flowable<List<Photo>> = repository.observePhotos()
}