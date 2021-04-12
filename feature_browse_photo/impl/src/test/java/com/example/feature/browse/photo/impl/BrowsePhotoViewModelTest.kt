package com.example.feature.browse.photo.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.feature.browse.photo.impl.ui.BrowsePhotoReducer
import com.example.feature.browse.photo.impl.ui.BrowsePhotoViewModel
import com.example.repositories.api.photo.entities.Photo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [BrowsePhotoViewModel]
 */
class BrowsePhotoViewModelTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val photoId: Long = 1L
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase = mock()
    private val reducer: BrowsePhotoReducer = mock()
    private val photo: Photo = mock()

    private lateinit var viewModel: BrowsePhotoViewModel

    @Before
    fun setUp() {
        viewModel = BrowsePhotoViewModel(
            photoId = photoId,
            getPhotoByIdUseCase = getPhotoByIdUseCase,
            reducer = reducer
        )
    }

    @Test
    fun `WHEN photo available THEN pass it into reducer`() {
        // prepare
        whenever(getPhotoByIdUseCase(photoId)).thenReturn(Single.just(photo))

        // do
        viewModel.init()

        // assert
        verify(reducer).setPhoto(photo)
    }
}