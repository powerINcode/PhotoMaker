package com.example.feature.photo.gallery.impl

import com.example.core.test.RxJavaTestRule
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.PhotoGalleryState
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryReducer
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.repositories.api.photo.entities.Photo
import org.joda.time.Instant
import org.joda.time.LocalDate
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [PhotoGalleryReducerTest]
 */
class PhotoGalleryReducerTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val reducer: PhotoGalleryReducer = PhotoGalleryReducer()

    @Test
    fun `test set photos`() {
        // prepare
        val createdAt = Instant.now()
        val photo = Photo(
            id = 1,
            name = "name",
            path = "path",
            createdAt = createdAt
        )
        val photos = listOf(photo)

        val expected = listOf(
            PhotoGalleryAdapter.Model(
                id = photo.id.toString(),
                name = photo.name,
                date = PhotoGalleryReducer.DATE_FORMATTER.print(LocalDate(photo.createdAt.millis)),
                path = photo.path
            )
        )

        // do
        val testObserver = reducer.stateObservable.test()
        reducer.setPhotos(photos)

        // assert
        testObserver
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1, PhotoGalleryState.EMPTY.copy(photos = expected))
    }

    @Test
    fun `test container size change`() {
        // prepare
        val spanCount = 1
        val itemSize = 100

        val expected = PhotoGalleryState.EMPTY.copy(
            spanCount = spanCount,
            photoSize = itemSize
        )

        // do
        val testObserver = reducer.stateObservable.test()
        reducer.containerSizeChange(spanCount, itemSize)

        // assert
        testObserver.assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(1, expected)
    }
}