package com.example.feature.browse.photo.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract
import com.example.feature.browse.photo.impl.ui.BrowsePhotoReducer
import com.example.repositories.api.photo.entities.Photo
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [BrowsePhotoReducer]
 */
class BrowsePhotoReducerTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private lateinit var reducer: BrowsePhotoReducer

    @Before
    fun setUp() {
        reducer = BrowsePhotoReducer()
    }

    @Test
    fun `test set photo`() {
        // prepare
        val photo = mockk<Photo>()
        val expected = BrowsePhotoContract.BrowsePhotoState.EMPTY.copy(
            photo = photo
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.setPhoto(photo)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }
}