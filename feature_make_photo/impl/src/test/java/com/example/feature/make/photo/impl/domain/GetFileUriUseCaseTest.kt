package com.example.feature.make.photo.impl.domain

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Test for [GetFileUriUseCase]
 */
class GetFileUriUseCaseTest {

    private val application: Application = mockk()

    private lateinit var useCae: GetFileUriUseCase

    private val file: File = mockk()
    private val uri: Uri = mockk()

    @Before
    fun setUp() {
        useCae = GetFileUriUseCase(application = application)
    }

    @Test
    fun `test get file`() {
        // prepare
        mockkStatic(FileProvider::class)
        every { FileProvider.getUriForFile(application, "com.example.photomaker.fileprovider", file) } returns uri

        // do
        val testObserver = useCae(file).test()

        //assert
        testObserver
            .assertNoErrors()
            .assertValue(uri)
    }
}