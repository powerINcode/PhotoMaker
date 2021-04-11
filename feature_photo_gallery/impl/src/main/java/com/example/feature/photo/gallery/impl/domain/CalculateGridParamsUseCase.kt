package com.example.feature.photo.gallery.impl.domain

import com.example.core.sreams.onIo
import com.example.domain.SingleUseCase
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase.Params
import com.example.feature.photo.gallery.impl.domain.CalculateGridParamsUseCase.Result
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CalculateGridParamsUseCase @Inject constructor(): SingleUseCase<Params, Result> {

    override fun invoke(params: Params): Single<Result> {
        return Single.fromCallable {
            var result = params.defaultSize
            while (result >= 0 && params.width % result != 0) {
                result--
            }

            Result(
                spanCount = params.width / result,
                itemSize = result
            )
        }.onIo()
    }

    data class Params(
        val width: Int,
        val defaultSize: Int
    )

    data class Result(
        val spanCount: Int,
        val itemSize: Int
    )
}