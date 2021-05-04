package com.example.feature.photo.gallery.impl.domain

import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

internal class ObservePhotosUseCaseImpl @Inject constructor(
    private val repository: com.example.api.PhotoRepository
): ObservePhotosUseCase {

    override fun invoke(params: Unit): Flowable<List<com.example.api.entities.Photo>> = repository.observePhotos()
}