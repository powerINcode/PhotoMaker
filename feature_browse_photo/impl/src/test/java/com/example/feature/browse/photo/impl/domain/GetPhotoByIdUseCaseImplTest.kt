package com.example.feature.browse.photo.impl.domain

import com.example.core.test.RxJavaTestRule
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import io.mockk.every
import io.mockk.mockk
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

    private val photoRepository: PhotoRepository = mockk()

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
        val photo = mockk<Photo>()
        every { photoRepository.getPhoto(photoId) } returns Single.just(photo)

        // do
        val testObserver = useCase(photoId).test()

        // assert
        testObserver
            .assertNoErrors()
            .assertValue(photo)
    }
}