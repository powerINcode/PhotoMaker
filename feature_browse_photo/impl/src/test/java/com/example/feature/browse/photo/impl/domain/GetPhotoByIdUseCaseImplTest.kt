package com.example.feature.browse.photo.impl.domain

import com.example.core.test.RxJavaTestRule
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for [GetPhotoByIdUseCaseImpl]
 */
class GetPhotoByIdUseCaseImplTest {
    @Rule
    @JvmField
    val rxRule = RxJavaTestRule()

    private lateinit var useCase: GetPhotoByIdUseCaseImpl

    private val photoRepository: PhotoRepository = mock()

    @Before
    fun setUp() {
        useCase = GetPhotoByIdUseCaseImpl(
            photoRepository = photoRepository
        )
    }

    @Test
    fun `test calculation`() {
        // prepare
        val photoId = 1L
        val photo = mock<Photo>()
        whenever(photoRepository.getPhoto(photoId)).thenReturn(Single.just(photo))

        // do
        val testObserver = useCase(photoId).test()

        // assert
        testObserver
            .assertNoErrors()
            .assertValue(photo)
    }
}