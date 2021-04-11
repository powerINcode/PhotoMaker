package com.example.feature.make.photo.impl.domain

import android.app.Application
import android.os.Environment
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Test for [CreateFileUseCase]
 */
class CreateFileUseCaseTest {

    private val application: Application = mock()

    private lateinit var useCae: CreateFileUseCase

    private val storageDir: File = mock()
    private val file: File = mock()

    @Before
    fun setUp() {
        useCae = CreateFileUseCase(application = application)
    }

    @Test
    fun `test create file`() {
        // prepare
        mockkStatic(File::class)
        every { File.createTempFile(any(), any(), any()) } returns storageDir
        whenever(application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).thenReturn(file)

        // do
        val testObserver = useCae().test()

        //assert
        testObserver
            .assertNoErrors()
            .assertComplete()
    }
}