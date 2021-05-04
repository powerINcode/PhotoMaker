package com.example.feature.make.photo.impl.domain

import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test

/**
 * Test for [SavePhotoPathUseCaseImpl]
 */
class SavePhotoPathUseCaseImplTest {

    private val photoRepository: com.example.api.PhotoRepository = mockk()

    private lateinit var useCae: SavePhotoPathUseCaseImpl

    @Before
    fun setUp() {
        useCae = SavePhotoPathUseCaseImpl(photoRepository = photoRepository)
    }

    @Test
    fun `test save photo`() {
        // prepare
        val params = SavePhotoPathUseCase.Params("name", path = "path")
        every { photoRepository.savePhoto(params.name, params.path) } returns Completable.complete()

        // do
        val testObserver = useCae(params).test()

        //assert
        testObserver
            .assertNoErrors()
            .assertComplete()
    }
}