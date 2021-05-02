package com.example.feature.photo.gallery.impl.ui

import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature_make_photo.api.MakePhotoFlowConfig
import com.example.ui.mvi.presenter.BasePresenter
import com.example.ui.navigation.FeatureCommand
import com.example.ui.navigation.Navigator
import javax.inject.Inject

internal class PhotoGalleryPresenter @Inject constructor(
    private val navigator: Navigator
): BasePresenter<PhotoGalleryContract.PhotoGalleryState, PhotoGalleryViewModel>() {

    override fun onCreated() {
        super.onCreated()

        intentOf<PhotoGalleryContract.PhotoGalleryIntent.ContainerSizeChange>()
            .switchMapCompletable { intent ->
                viewModel.calculatePhotoSize(intent.containerSize, intent.defaultItemSize)
                    .onErrorComplete()
            }
            .subscribeTillDestroy()

        intentOf<PhotoGalleryContract.PhotoGalleryIntent.MakePhoto>()
            .subscribeTillDestroy { navigator.navigate(FeatureCommand(MakePhotoFlowConfig)) }

        intentOf<PhotoGalleryContract.PhotoGalleryIntent.PhotoClick>()
            .subscribeTillDestroy { intent ->
                navigator.navigate(FeatureCommand(BrowsePhotoFlowConfig(intent.photoId)))
            }
    }
}