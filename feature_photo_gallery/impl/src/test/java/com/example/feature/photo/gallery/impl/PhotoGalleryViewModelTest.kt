package com.example.feature.photo.gallery.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryReducer
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryViewModel
import com.example.repositories.api.photo.entities.Photo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
        val result = CalculateGridParamsUseCase.Result(2,2)
        whenever(calculatePhotoSizeUseCase(params)).thenReturn(Single.just(result))

        // do
        viewModel.init()
        viewModel.send(PhotoGalleryContract.PhotoGalleryIntent.ContainerSizeChange(1, 1))

        // assert
        verify(calculatePhotoSizeUseCase).invoke(params)
        verify(reducer).containerSizeChange(result.spanCount, result.itemSize)
    }
}