package com.example.feature.photo.gallery.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryReducer
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryViewModel
import com.example.repositories.api.photo.entities.Photo
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
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

    private val calculatePhotoSizeUseCase: CalculateGridParamsUseCase = mockk()
    private val observePhotosUseCase: ObservePhotosUseCase = mockk()
    private val reducer: PhotoGalleryReducer = mockk()

    private lateinit var viewModel: PhotoGalleryViewModel

    @Before
    fun setUp() {
        every { observePhotosUseCase()} returns Flowable.empty()

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
        every { observePhotosUseCase()} returns Flowable.just(photos)

        // do
        viewModel.init()

        // assert
        verify { observePhotosUseCase() }
        verify { reducer.setPhotos(photos) }
    }

    @Test
    fun `WHEN container change THEN calculate new photo size`() {
        // prepare
        val containerSize = 1
        val defaultItemSize = 1
        val expected = CalculateGridParamsUseCase.Result(2, 2)
        every { calculatePhotoSizeUseCase(any()) } returns Single.just(expected)
        justRun { reducer.containerSizeChange(spanCount = expected.spanCount, itemSize = expected.itemSize) }

        // do
        viewModel.calculatePhotoSize(containerSize, defaultItemSize).test()
            .assertNoErrors()
            .assertComplete()

        // verify
        verify { reducer.containerSizeChange(spanCount = expected.spanCount, itemSize = expected.itemSize) }

    }
}