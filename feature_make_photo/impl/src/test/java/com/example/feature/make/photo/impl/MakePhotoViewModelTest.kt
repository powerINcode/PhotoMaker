package com.example.feature.make.photo.impl

import android.net.Uri
import com.example.core.test.RxJavaTestRule
import com.example.feature.make.photo.impl.domain.CreateFileUseCase
import com.example.feature.make.photo.impl.domain.DeleteFileUseCase
import com.example.feature.make.photo.impl.domain.GetFileUriUseCase
import com.example.feature.make.photo.impl.ui.MakePhotoReducer
import com.example.feature.make.photo.impl.ui.MakePhotoViewModel
import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [MakePhotoViewModel]
 */
class MakePhotoViewModelTest {

    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val createFileUseCase: CreateFileUseCase = mockk()
    private val getFileUriUseCase: GetFileUriUseCase = mockk()
    private val savePhotoPathUseCase: SavePhotoPathUseCase = mockk()
    private val deleteFileUseCase: DeleteFileUseCase = mockk()
    private val reducer: MakePhotoReducer = mockk()

    private lateinit var viewModel: MakePhotoViewModel

    private val fileUri = mockk<Uri>()

    @Before
    fun setUp() {
        every { fileUri.toString() } returns ""
        every { createFileUseCase() } returns Single.just(mockk())
        every { getFileUriUseCase(any()) } returns Single.just(fileUri)
        every { deleteFileUseCase(any()) } returns Completable.complete()
        every { savePhotoPathUseCase(any()) } returns Completable.complete()
        justRun { reducer.startMakingPhoto() }
        justRun { reducer.photoMade(fileUri) }
        justRun { reducer.startSavePhoto() }

        viewModel = MakePhotoViewModel(
            createFileUseCase = createFileUseCase,
            getFileUriUseCase = getFileUriUseCase,
            savePhotoPathUseCase = savePhotoPathUseCase,
            deleteFileUseCase = deleteFileUseCase,
            reducer = reducer
        )
    }

    @Test
    fun deleteFile() {
        // do
        viewModel.makePhoto().test()
            .assertNoErrors()
            .assertComplete()

        viewModel.deleteFile().test()
            .assertNoErrors()
            .assertComplete()

        // verify
        verify { deleteFileUseCase(fileUri) }
    }

    @Test
    fun photoMade() {
        // prepare
        justRun { reducer.photoMade(fileUri) }

        // do
        viewModel.makePhoto().test()
            .assertNoErrors()
            .assertComplete()

        viewModel.photoMade().test()
            .assertNoErrors()
            .assertComplete()

        // verify
        verify { reducer.photoMade(fileUri) }
    }

    @Test
    fun savePhoto() {
        // prepare
        val name = "name"
        val fileUriString = "fileUriString"
        every { fileUri.toString() } returns fileUriString

        // do
        viewModel.makePhoto().test()
            .assertNoErrors()
            .assertComplete()

        viewModel.photoMade().test()
            .assertNoErrors()
            .assertComplete()

        viewModel.savePhoto(name).test()
            .assertNoErrors()
            .assertValue(Unit)

        //verify
        verify { reducer.startSavePhoto() }
        verify {
            savePhotoPathUseCase(
                SavePhotoPathUseCase.Params(
                    name = name,
                    path = fileUriString
                )
            )
        }
    }

    @Test
    fun makePhoto() {
        //do
        viewModel.makePhoto().test()
            .assertNoErrors()
            .assertComplete()

        // verify
        verify { reducer.startMakingPhoto() }
        verify { createFileUseCase() }
        verify { getFileUriUseCase(any()) }
    }
}