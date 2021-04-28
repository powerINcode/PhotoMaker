package com.example.feature.make.photo.impl.domain

import android.net.Uri
import com.example.domain.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import java.io.File
import javax.inject.Inject

internal class DeleteFileUseCase @Inject constructor() : CompletableUseCase<Uri> {

    override fun invoke(params: Uri): Completable = Completable.fromAction {
        val file = File(params.toString())
        if (file.exists()) {
            file.delete()
        }
    }
}