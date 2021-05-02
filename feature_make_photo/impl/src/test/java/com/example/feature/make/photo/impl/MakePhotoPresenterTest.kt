package com.example.feature.make.photo.impl

import android.net.Uri
import com.example.core.test.RxJavaTestRule
import com.example.feature.make.photo.impl.ui.MakePhotoContract
import com.example.feature.make.photo.impl.ui.MakePhotoPresenter
import com.example.feature.make.photo.impl.ui.MakePhotoViewModel
import com.example.ui.navigation.CreatePhotoCommand
import com.example.ui.navigation.Finish
import com.example.ui.navigation.Navigator
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [MakePhotoPresenter]
 */
internal class MakePhotoPresenterTest {

    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val navigator: Navigator = mockk()
    private val viewModel: MakePhotoViewModel = mockk()

    lateinit var presenter: MakePhotoPresenter

    @Before
    fun setUp() {
        justRun { viewModel.init() }
        presenter = MakePhotoPresenter(
            navigator = navigator
        )
        presenter.onCreate(viewModel)
    }

    @Test
    fun `WHEN file created successfully THEN navigate to to make photo`() {
        // prepare
        val uri = mockk<Uri>()
        every { viewModel.makePhoto() } returns Maybe.just(uri)

        // do
        presenter.onCreated()

        // assert
        verify { viewModel.makePhoto() }

        verify { navigator.navigate(CreatePhotoCommand(MakePhotoContract.REQUEST_CODE_MAKE_PHOTO, uri)) }
    }

    @Test
    fun `WHEN user click back THEN remove not saved file`() {
        // prepare
        every { viewModel.deleteFile() } returns Completable.complete()
        presenter.onCreated()

        // do
        presenter.send(MakePhotoContract.MakePhotoIntent.Back)

        // assert
        verify { viewModel.deleteFile() }
    }

    @Test
    fun `WHEN photo made THEN put it into reducer`() {
        // prepare
        every { viewModel.photoMade() } returns Completable.complete()

        // do
        presenter.onCreated()
        presenter.send(MakePhotoContract.MakePhotoIntent.PhotoMade)

        // assert
        verify { viewModel.photoMade() }
    }


    @Test
    fun `WHEN photo saved and name not empty THEN close the screen`() {
        // prepare
        val name = "name"

        every { viewModel.savePhoto(any()) } returns Maybe.just(Unit)

        // do
        presenter.onCreated()
        presenter.send(MakePhotoContract.MakePhotoIntent.SavePhoto(name))

        // assert
        verify { viewModel.savePhoto(name) }
        verify { navigator.navigate(Finish) }
    }
}