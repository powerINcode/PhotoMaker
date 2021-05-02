package com.example.feature.make.photo.impl.domain

import android.app.Application
import android.os.Environment
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Test for [CreateFileUseCase]
 */
class CreateFileUseCaseTest {

    private val application: Application = mockk()

    private lateinit var useCae: CreateFileUseCase

    private val storageDir: File = mockk()
    private val file: File = mockk()

    @Before
    fun setUp() {
        useCae = CreateFileUseCase(application = application)
    }

    @Test
    fun `test create file`() {
        // prepare
        mockkStatic(File::class)
        every { File.createTempFile(any(), any(), any()) } returns storageDir
        every { application.getExternalFilesDir(Environment.DIRECTORY_PICTURES) } returns file

        // do
        val testObserver = useCae().test()

        //assert
        testObserver
            .assertNoErrors()
            .assertComplete()
    }
}