package com.example.feature.photo.gallery.impl.ui

import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.PhotoGalleryState
import com.example.ui.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

internal class PhotoGalleryViewModel @Inject constructor(
    private val calculatePhotoSizeUseCase: CalculateGridParamsUseCase,
    private val observePhotosUseCase: ObservePhotosUseCase,
    reducer: PhotoGalleryReducer
) : BaseViewModel<PhotoGalleryState, PhotoGalleryReducer>(reducer) {

    override fun doInit() {
        observePhotosUseCase()
            .subscribeTillClear {
                reducer.setPhotos(it)
            }
    }

    fun calculatePhotoSize(containerSize: Int, defaultItemSize: Int): Completable {
        return calculatePhotoSizeUseCase(
            CalculateGridParamsUseCase.Params(
                width = containerSize,
                defaultSize = defaultItemSize
            )
        )
            .doOnSuccess { result ->
                reducer.containerSizeChange(spanCount = result.spanCount, itemSize = result.itemSize)
            }
            .ignoreElement()
    }
}