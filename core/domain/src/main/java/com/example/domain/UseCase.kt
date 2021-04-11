package com.example.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UseCase<Params, Result> {
    operator fun invoke(params: Params): Result
}

interface SingleUseCase<Params, Result>: UseCase<Params, Single<Result>>

interface SimpleUseCase<Result>: SingleUseCase<Unit, Result> {
    operator fun invoke(): Single<Result> {
        return invoke(Unit)
    }
}

interface CompletableUseCase<Params>: UseCase<Params, Completable>