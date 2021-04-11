package com.example.feature.make.photo.impl.domain

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.domain.SingleUseCase
import io.reactivex.rxjava3.core.Single
import java.io.File
import javax.inject.Inject

class GetFileUriUseCase @Inject constructor(
    private val application: Application
) : SingleUseCase<File, Uri> {

    override fun invoke(params: File): Single<Uri> = Single.fromCallable {
        FileProvider.getUriForFile(
            application,
            "com.example.photomaker.fileprovider",
            params
        )
    }
}