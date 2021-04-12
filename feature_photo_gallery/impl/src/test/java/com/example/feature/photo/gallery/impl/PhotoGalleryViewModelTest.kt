package com.example.feature.photo.gallery.impl

import android.os.Bundle
import com.example.core.test.RxJavaTestRule
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryReducer
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryViewModel
import com.example.feature_make_photo.api.MakePhotoFlowConfig
import com.example.repositories.api.photo.entities.Photo
import com.example.ui.navigation.FeatureCommand
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.every
import io.mockk.just
import io.mockk.mockkConstructor
import io.mockk.runs
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.joda.time.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [PhotoGalleryViewModel]
 */
class PhotoGalleryViewModelTest {

    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val calculatePhotoSizeUseCase: CalculateGridParamsUseCase = mock()
    private val observePhotosUseCase: ObservePhotosUseCase = mock()
    private val reducer: PhotoGalleryReducer = mock()

    private lateinit var viewModel: PhotoGalleryViewModel

    @Before
    fun setUp() {
        whenever(observePhotosUseCase()).thenReturn(Flowable.empty())

        viewModel = PhotoGalleryViewModel(
            calculatePhotoSizeUseCase = calculatePhotoSizeUseCase,
            observePhotosUseCase = observePhotosUseCase,
            reducer = reducer
        )
    }

    @Test
    fun `WHEN photos available THEN put them into reducer`() {
        // prepare
        val createdAt = Instant.now()
        val photos = listOf(
            Photo(
                id = 1,
                name = "name",
                path = "path",
                createdAt = createdAt
            )
        )
        whenever(observePhotosUseCase()).thenReturn(Flowable.just(photos))

        // do
        viewModel.init()

        // assert
        verify(observePhotosUseCase).invoke()
        verify(reducer).setPhotos(photos)
    }

    @Test
    fun `WHEN container size change THEN calculate new one and put them into reducer`() {
        // prepare
        val params = CalculateGridParamsUseCase.Params(1, 1)
        val result = CalculateGridParamsUseCase.Result(2, 2)
        whenever(calculatePhotoSizeUseCase(params)).thenReturn(Single.just(result))

        // do
        viewModel.init()
        viewModel.send(PhotoGalleryContract.PhotoGalleryIntent.ContainerSizeChange(1, 1))

        // assert
        verify(calculatePhotoSizeUseCase).invoke(params)
        verify(reducer).containerSizeChange(result.spanCount, result.itemSize)
    }

    @Test
    fun `WHEN click on the make photo THEN navigate to the make photo feature`() {
        // prepare
        val testObserver = viewModel.navigationSubject.test()

        // do
        viewModel.init()
        viewModel.send(PhotoGalleryContract.PhotoGalleryIntent.MakePhoto)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(1)
            .assertValue(FeatureCommand(MakePhotoFlowConfig))
    }

    @Test
    fun `WHEN click on the photo THEN navigate to the browse photo feature`() {
        // prepare
        mockkConstructor(Bundle::class)
        every { anyConstructed<Bundle>().putLong(any(), any()) } just runs

        val photoId = 1L
        val testObserver = viewModel.navigationSubject.test()

        // do
        viewModel.init()
        viewModel.send(PhotoGalleryContract.PhotoGalleryIntent.PhotoClick(photoId))

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0) { command ->
                @Suppress("UNCHECKED_CAST")
                command as? FeatureCommand<BrowsePhotoFlowConfig> != null
            }
    }
}