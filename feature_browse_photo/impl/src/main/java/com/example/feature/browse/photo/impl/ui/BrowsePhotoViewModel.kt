package com.example.feature.browse.photo.impl.ui

import com.example.core.sreams.toMainThread
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoIntent.ShowDummyFragment
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.feature.browse.photo.impl.ui.dummy.DummyContract
import com.example.feature.browse.photo.impl.ui.dummy.DummyFragment
import com.example.ui.navigation.FragmentCommand
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

        intentOf<ShowDummyFragment>()
            .toMainThread()
            .subscribeTillClear {
                navigate(
                    FragmentCommand(
                        destination = DummyFragment(),
                        configuration = DummyContract.Configuration(phrase = "Dummy phrase !!!")
                    )
                )
            }
    }
}