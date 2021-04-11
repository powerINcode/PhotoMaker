package com.example.feature.make.photo.impl.domain

import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import com.example.repositories.api.photo.PhotoRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test

/**
 * Test for [SavePhotoPathUseCaseImpl]
 */
class SavePhotoPathUseCaseImplTest {

    private val photoRepository: PhotoRepository = mock()

    private lateinit var useCae: SavePhotoPathUseCaseImpl

    @Before
    fun setUp() {
        useCae = SavePhotoPathUseCaseImpl(photoRepository = photoRepository)
    }

    @Test
    fun `test save photo`() {
        // prepare
        val params = SavePhotoPathUseCase.Params("name", path = "path")
        whenever(photoRepository.savePhoto(params.name, params.path)).thenReturn(Completable.complete())

        // do
        val testObserver = useCae(params).test()

        //assert
        testObserver
            .assertNoErrors()
            .assertComplete()
    }
}