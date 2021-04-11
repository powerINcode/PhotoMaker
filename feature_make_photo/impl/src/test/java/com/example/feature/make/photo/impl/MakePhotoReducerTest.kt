package com.example.feature.make.photo.impl

import android.net.Uri
import com.example.core.test.RxJavaTestRule
import com.example.feature.make.photo.impl.ui.MakePhotoContract
import com.example.feature.make.photo.impl.ui.MakePhotoReducer
import com.example.ui.textview.ResourceText
import com.nhaarman.mockitokotlin2.mock
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [MakePhotoReducer]
 */
class MakePhotoReducerTest {

    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val reducer: MakePhotoReducer = MakePhotoReducer()

    @Before
    fun setUp() {
        mockkStatic(Uri::class)

        every { Uri.parse(any()) } returns mock()
    }

    @Test
    fun `test photo made`() {
        // prepare
        val uri = Uri.parse("")
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(photoUri = uri)

        // do
        val testObserver = reducer.stateObservable.test()
        reducer.photoMade(uri)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1, expected)
    }

    @Test
    fun `test photo not made`() {
        // prepare
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(error = ResourceText(R.string.make_photo_not_made))

        // do
        val testObserver = reducer.stateObservable.test()
        reducer.photoNotMade()

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1, expected)
    }
}