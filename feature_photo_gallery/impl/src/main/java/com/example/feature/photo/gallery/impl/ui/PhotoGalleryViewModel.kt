package com.example.feature.photo.gallery.impl.ui

import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.PhotoGalleryIntent
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.PhotoGalleryState
import com.example.feature_make_photo.api.MakePhotoFlowConfig
import com.example.ui.navigation.FeatureCommand
import com.example.ui.viewmodel.BaseViewModel
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

        intentOf<PhotoGalleryIntent.ContainerSizeChange>()
            .switchMapSingle { intent ->
                calculatePhotoSizeUseCase(
                    CalculateGridParamsUseCase.Params(
                        width = intent.containerSize,
                        defaultSize = intent.defaultItemSize
                    )
                )
            }
            .subscribeTillClear { result ->
                reducer.containerSizeChange(spanCount = result.spanCount, itemSize = result.itemSize)
            }

        intentOf<PhotoGalleryIntent.MakePhoto>()
            .subscribeTillClear { navigate(FeatureCommand(MakePhotoFlowConfig)) }

        intentOf<PhotoGalleryIntent.PhotoClick>()
            .subscribeTillClear { intent ->
                navigate(FeatureCommand(BrowsePhotoFlowConfig(intent.photoId)))
            }
    }
}