package com.example.feature.make.photo.impl.ui

import com.example.core.sreams.flatMapDropCompletable
import com.example.core.sreams.toUnit
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoIntent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.ui.mvi.presenter.BasePresenter
import com.example.ui.navigation.CreatePhotoCommand
import com.example.ui.navigation.Finish
import com.example.ui.navigation.Navigator
import javax.inject.Inject

internal class MakePhotoPresenter @Inject constructor(
    private val navigator: Navigator
): BasePresenter<MakePhotoState, MakePhotoViewModel>() {

    override fun onCreated() {
        super.onCreated()

        intentOf<MakePhotoIntent.Back>()
            .flatMapDropCompletable {
                viewModel.deleteFile()
                    .onErrorComplete()
                    .doOnComplete { navigator.navigate(Finish) }
            }
            .subscribeTillDestroy()

        intentOf<MakePhotoIntent.PhotoMade>()
            .flatMapDropCompletable {
                viewModel.photoMade()
            }
            .subscribeTillDestroy()

        intentOf<MakePhotoIntent.SavePhoto>()
            .flatMapDropCompletable { intent ->
                viewModel.savePhoto(intent.name)
                    .doOnSuccess { navigator.navigate(Finish) }
                    .ignoreElement()
            }
            .subscribeTillDestroy()

        intentOf<MakePhotoIntent.MakePhoto>()
            .toUnit()
            .startWithItem(Unit)
            .flatMapDropCompletable {
                viewModel.makePhoto()
                    .doOnSuccess { fileUri ->
                        navigator.navigate(CreatePhotoCommand(MakePhotoContract.REQUEST_CODE_MAKE_PHOTO, requireNotNull(fileUri)))
                    }
                    .ignoreElement()
            }
            .subscribeTillDestroy()
    }
}