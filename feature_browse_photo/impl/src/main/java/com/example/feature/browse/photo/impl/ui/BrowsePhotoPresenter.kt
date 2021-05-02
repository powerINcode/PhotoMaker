package com.example.feature.browse.photo.impl.ui

import com.example.core.sreams.toMainThread
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoIntent
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.feature.browse.photo.impl.ui.dummy.DummyContract
import com.example.feature.browse.photo.impl.ui.dummy.DummyFragment
import com.example.ui.mvi.presenter.BasePresenter
import com.example.ui.navigation.FragmentCommand
import com.example.ui.navigation.Navigator
import javax.inject.Inject

internal class BrowsePhotoPresenter @Inject constructor(
    private val navigator: Navigator
): BasePresenter<BrowsePhotoState, BrowsePhotoViewModel>() {

    override fun onCreated() {
        super.onCreated()

        intentOf<BrowsePhotoIntent.ShowDummyFragment>()
            .toMainThread()
            .subscribeTillDestroy {
                navigator.navigate(
                    FragmentCommand(
                        destination = DummyFragment(),
                        configuration = DummyContract.Configuration(phrase = "Dummy phrase !!!")
                    )
                )
            }
    }
}