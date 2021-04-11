package com.example.feature.make.photo.impl.ui

import android.net.Uri
import com.example.core.sreams.flatMapDropCompletable
import com.example.feature.make.photo.impl.domain.CreateFileUseCase
import com.example.feature.make.photo.impl.domain.DeleteFileUseCase
import com.example.feature.make.photo.impl.domain.GetFileUriUseCase
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoIntent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.feature_make_photo.api.SavePhotoPathUseCase
import com.example.ui.navigation.CreatePhotoCommand
import com.example.ui.navigation.Finish
import com.example.ui.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class MakePhotoViewModel @Inject constructor(
    private val createFileUseCase: CreateFileUseCase,
    private val getFileUriUseCase: GetFileUriUseCase,
    private val savePhotoPathUseCase: SavePhotoPathUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    reducer: MakePhotoReducer
) : BaseViewModel<MakePhotoState, MakePhotoReducer>(reducer) {

    private var fileUri: Uri? = null

    init {
        intentOf<MakePhotoIntent.Back>()
            .flatMapDropCompletable {
                deleteFileUseCase(requireNotNull(fileUri))
                    .onErrorComplete()
                    .doOnComplete { navigate(Finish) }
            }
            .subscribeTillClear()

        intentOf<MakePhotoIntent.PhotoMade>()
            .subscribeTillClear {
                val fileUri = requireNotNull(fileUri)
                reducer.photoMade(fileUri)
            }

        intentOf<MakePhotoIntent.SavePhoto>()
            .flatMapDropCompletable { intent ->
                if (intent.name.isBlank()) {
                    reducer.photoNameEmpty()
                    Completable.complete()
                } else {
                    reducer.startSavePhoto()
                    val path = requireNotNull(fileUri)
                    val params = SavePhotoPathUseCase.Params(
                        name = intent.name,
                        path = path.toString()
                    )

                    savePhotoPathUseCase(params)
                        .doOnError { reducer.photoNotSaved(it.message) }
                        .doOnComplete { navigate(Finish) }
                        .onErrorComplete()
                }
            }
            .subscribeTillClear()

        intentOf<MakePhotoIntent.MakePhoto>()
            .flatMapDropCompletable {
                createFileUseCase()
                    .flatMap { file -> getFileUriUseCase(file) }
                    .doOnSuccess { uri ->
                        fileUri = uri
                        navigate(CreatePhotoCommand(MakePhotoContract.REQUEST_CODE_MAKE_PHOTO, requireNotNull(fileUri)))
                    }
                    .ignoreElement()
                    .doOnError { reducer.createPhotoFileError(it.message) }
                    .onErrorComplete()
            }
            .subscribeTillClear()

        send(MakePhotoIntent.MakePhoto)
    }
}