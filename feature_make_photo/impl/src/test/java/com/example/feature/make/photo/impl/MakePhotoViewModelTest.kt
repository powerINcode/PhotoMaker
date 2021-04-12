package com.example.feature.make.photo.impl

import android.net.Uri
import com.example.core.test.RxJavaTestRule
import com.example.feature.make.photo.impl.domain.CreateFileUseCase
import com.example.feature.make.photo.impl.domain.DeleteFileUseCase
import com.example.feature.make.photo.impl.domain.GetFileUriUseCase
import com.example.feature.make.photo.impl.ui.MakePhotoContract
import com.example.feature.make.photo.impl.ui.MakePhotoReducer
import com.example.feature.make.photo.impl.ui.MakePhotoViewModel
import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import com.example.ui.navigation.CreatePhotoCommand
import com.example.ui.navigation.Finish
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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

    private val createFileUseCase: CreateFileUseCase = mock()
    private val getFileUriUseCase: GetFileUriUseCase = mock()
    private val savePhotoPathUseCase: SavePhotoPathUseCase = mock()
    private val deleteFileUseCase: DeleteFileUseCase = mock()
    private val reducer: MakePhotoReducer = mock()

    private lateinit var viewModel: MakePhotoViewModel

    private val fileUri = mock<Uri>()

    @Before
    fun setUp() {
        whenever(fileUri.toString()).thenReturn("")
        whenever(createFileUseCase()).thenReturn(Single.just(mock()))
        whenever(getFileUriUseCase(any())).thenReturn(Single.just(fileUri))

        viewModel = MakePhotoViewModel(
            createFileUseCase = createFileUseCase,
            getFileUriUseCase = getFileUriUseCase,
            savePhotoPathUseCase = savePhotoPathUseCase,
            deleteFileUseCase = deleteFileUseCase,
            reducer = reducer
        )
    }

    @Test
    fun `WHEN file created successfully THEN navigate to to make photo`() {
        // do
        val testObserver = viewModel.navigationSubject.test()
        viewModel.init()

        // assert
        verify(createFileUseCase).invoke()
        verify(getFileUriUseCase).invoke(any())

        testObserver.assertNoErrors()
            .assertValueCount(1)
            .assertValue(CreatePhotoCommand(MakePhotoContract.REQUEST_CODE_MAKE_PHOTO, fileUri))
    }

    @Test
    fun `WHEN user click back THEN remove not saved file`() {
        // prepare
        whenever(deleteFileUseCase(any())).thenReturn(Completable.complete())

        // do
        viewModel.init()
        viewModel.send(MakePhotoContract.MakePhotoIntent.Back)

        // assert
        verify(deleteFileUseCase).invoke(any())
    }

    @Test
    fun `WHEN photo made THEN put it into reducer`() {
        // do
        viewModel.init()
        viewModel.send(MakePhotoContract.MakePhotoIntent.PhotoMade)

        // assert
        verify(reducer).photoMade(any())
    }

    @Test
    fun `WHEN photo saved and name not empty THEN close the screen`() {
        // prepare
        val name = "name"
        val path = ""
        val params = SavePhotoPathUseCase.Params(
            name = name,
            path = path
        )

        whenever(savePhotoPathUseCase(params)).thenReturn(Completable.complete())

        // do
        viewModel.init()
        val testObserver = viewModel.navigationSubject.test()
        viewModel.send(MakePhotoContract.MakePhotoIntent.PhotoMade)
        viewModel.send(MakePhotoContract.MakePhotoIntent.SavePhoto(name))

        // assert
        verify(reducer).startSavePhoto()

        verify(savePhotoPathUseCase).invoke(params)
        testObserver.assertNoErrors()
            .assertValueCount(1)
            .assertValue(Finish)
    }
}