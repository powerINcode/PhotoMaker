package com.example.feature.make.photo.impl.domain

import android.app.Application
import android.os.Environment
import com.example.domain.SimpleUseCase
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateFileUseCase @Inject constructor(
    private val application: Application
) : SimpleUseCase<File> {

    override fun invoke(params: Unit): Single<File> = Single.fromCallable {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: throw IOException("Can't get a pictures folder")
        File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}