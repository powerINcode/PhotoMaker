package com.example.feature.make.photo.impl

import android.net.Uri
import com.example.core.test.RxJavaTestRule
import com.example.feature.make.photo.impl.ui.MakePhotoContract
import com.example.feature.make.photo.impl.ui.MakePhotoReducer
import com.example.ui.textview.PureText
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
        val testObserver = reducer.observeStateChange().test()
        reducer.photoMade(uri)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test photo not made`() {
        // prepare
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(error = ResourceText(R.string.make_photo_not_made))

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.photoNotMade()

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test photo name empty`() {
        // prepare
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(
            error = ResourceText(R.string.make_photo_empty)
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.photoNameEmpty()

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test photo start making photo`() {
        // prepare
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(
            error = null
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.startMakingPhoto()

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test photo start saving photo`() {
        // prepare
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(
            loading = true,
            error = null
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.startSavePhoto()

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test create photo file error`() {
        // prepare
        val error = "error"
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(
            loading = false,
            error = PureText(error)
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.createPhotoFileError(error)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }

    @Test
    fun `test photo not saved`() {
        // prepare
        val error = "error"
        val expected = MakePhotoContract.MakePhotoState.EMPTY.copy(
            loading = false,
            error = PureText(error)
        )

        // do
        val testObserver = reducer.observeStateChange().test()
        reducer.createPhotoFileError(error)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1) { stateChange -> stateChange.state == expected }
    }
}