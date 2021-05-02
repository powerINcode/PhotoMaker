package com.example.feature.make.photo.impl.ui

import android.net.Uri
import com.example.feature.make.photo.impl.domain.CreateFileUseCase
import com.example.feature.make.photo.impl.domain.DeleteFileUseCase
import com.example.feature.make.photo.impl.domain.GetFileUriUseCase
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import com.example.ui.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

internal class MakePhotoViewModel @Inject constructor(
    private val createFileUseCase: CreateFileUseCase,
    private val getFileUriUseCase: GetFileUriUseCase,
    private val savePhotoPathUseCase: SavePhotoPathUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    reducer: MakePhotoReducer
) : BaseViewModel<MakePhotoState, MakePhotoReducer>(reducer) {

    private var fileUri: Uri? = null
    private var photoMadeSuccessfully: Boolean = false

    override fun doInit() {

    }

    fun deleteFile(): Completable {
        return deleteFileUseCase(requireNotNull(fileUri))
    }

    fun photoMade(): Completable {
        return Completable.fromAction {
            photoMadeSuccessfully = true
            reducer.photoMade(requireNotNull(fileUri))
        }
    }

    fun savePhoto(name: String): Maybe<Unit> {
        return when {
            name.isBlank() -> {
                reducer.photoNameEmpty()
                Maybe.empty()
            }
            !photoMadeSuccessfully -> {
                reducer.photoNotMade()
                Maybe.empty()
            }
            else -> {
                reducer.startSavePhoto()
                val path = requireNotNull(fileUri)
                val params = SavePhotoPathUseCase.Params(
                    name = name,
                    path = path.toString()
                )

                savePhotoPathUseCase(params)
                    .doOnError { reducer.photoNotSaved(it.message) }
                    .andThen(Maybe.just(Unit))
                    .onErrorComplete()
            }
        }
    }

    fun makePhoto(): Maybe<Uri> {
        reducer.startMakingPhoto()
        photoMadeSuccessfully = false

        return createFileUseCase()
            .flatMap { file -> getFileUriUseCase(file) }
            .doOnSuccess { uri -> fileUri = uri }
            .doOnError { reducer.createPhotoFileError(it.message) }
            .onErrorComplete()
    }
}