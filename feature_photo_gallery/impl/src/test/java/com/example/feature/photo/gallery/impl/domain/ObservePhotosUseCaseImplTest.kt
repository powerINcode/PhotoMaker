package com.example.feature.photo.gallery.impl.domain

import com.example.core.test.RxJavaTestRule
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Flowable
import org.joda.time.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [ObservePhotosUseCaseImpl]
 */
class ObservePhotosUseCaseImplTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private val repository: PhotoRepository = mockk()

    private lateinit var useCase: ObservePhotosUseCaseImpl

    @Before
    fun setUp() {
        useCase = ObservePhotosUseCaseImpl(
            repository = repository
        )
    }

    @Test
    fun `test observe photos`() {
        // prepare
        val photo1 = Photo(1, "name", "path", Instant.now())
        val photo2 = Photo(2, "name", "path", Instant.now())
        val expected1 = listOf(photo1)
        val expected2 = listOf(photo1, photo2)

        every { repository.observePhotos()} returns Flowable.just(expected1, expected2)

        // do
        val testObserver = useCase().test()

        // assert
        testObserver
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0, expected1)
            .assertValueAt(1, expected2)
    }
}