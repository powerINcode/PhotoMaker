package com.example.feature.browse.photo.impl.ui

import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.ui.viewmodel.BaseViewModel
import javax.inject.Inject

internal class BrowsePhotoViewModel @Inject constructor(
    private val configuration: BrowsePhotoFlowConfig,
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    reducer: BrowsePhotoReducer
) : BaseViewModel<BrowsePhotoState, BrowsePhotoReducer>(reducer) {

    override fun doInit() {
        getPhotoByIdUseCase(configuration.photoId)
            .subscribeTillClear { reducer.setPhoto(it) }
    }
}