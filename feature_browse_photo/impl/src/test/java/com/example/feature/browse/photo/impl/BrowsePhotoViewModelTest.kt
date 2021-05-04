package com.example.feature.browse.photo.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.feature.browse.photo.impl.ui.BrowsePhotoReducer
import com.example.feature.browse.photo.impl.ui.BrowsePhotoViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase = mockk()
    private val reducer: BrowsePhotoReducer = mockk()
    private val photo: com.example.api.entities.Photo = mockk()

    private lateinit var viewModel: BrowsePhotoViewModel

    @Before
    fun setUp() {
        viewModel = BrowsePhotoViewModel(
            configuration = BrowsePhotoFlowConfig(photoId),
            getPhotoByIdUseCase = getPhotoByIdUseCase,
            reducer = reducer
        )
    }

    @Test
    fun `WHEN photo available THEN pass it into reducer`() {
        // prepare
        every { getPhotoByIdUseCase(photoId)} returns Single.just(photo)

        // do
        viewModel.init()

        // assert
        verify {reducer.setPhoto(photo) }
    }
}