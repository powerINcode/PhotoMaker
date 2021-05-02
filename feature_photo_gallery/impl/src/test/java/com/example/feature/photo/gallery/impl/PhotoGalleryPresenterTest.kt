package com.example.feature.photo.gallery.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryPresenter
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryViewModel
import com.example.feature_make_photo.api.MakePhotoFlowConfig
import com.example.ui.navigation.FeatureCommand
import com.example.ui.navigation.Navigator
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [PhotoGalleryPresenter]
 */
internal class PhotoGalleryPresenterTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val navigator: Navigator = mockk()
    private val viewModel: PhotoGalleryViewModel = mockk()

    lateinit var presenter: PhotoGalleryPresenter

    @Before
    fun setUp() {
        justRun { viewModel.init() }
        presenter = PhotoGalleryPresenter(
            navigator = navigator
        )

        presenter.onCreate(viewModel)
    }

    @Test
    fun `WHEN container size change THEN calculate new one and put them into reducer`() {
        // do
        presenter.onCreated()
        presenter.send(PhotoGalleryContract.PhotoGalleryIntent.ContainerSizeChange(1, 1))

        // assert
        verify { viewModel.calculatePhotoSize(1, 1) }
    }

    @Test
    fun `WHEN click on the make photo THEN navigate to the make photo feature`() {
        // do
        presenter.onCreated()
        presenter.send(PhotoGalleryContract.PhotoGalleryIntent.MakePhoto)

        // assert
        verify { navigator.navigate(FeatureCommand(MakePhotoFlowConfig)) }
    }

    @Test
    fun `WHEN click on the photo THEN navigate to the browse photo feature`() {
        // prepare
        val photoId = 1L

        // do
        presenter.onCreated()
        presenter.send(PhotoGalleryContract.PhotoGalleryIntent.PhotoClick(photoId))

        // assert
        verify { navigator.navigate(FeatureCommand(BrowsePhotoFlowConfig(photoId))) }
    }
}