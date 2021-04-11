package com.example.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface UseCase<Params, Result> {
    operator fun invoke(params: Params): Result
}

interface SingleUseCase<Params, Result>: UseCase<Params, Single<Result>>

interface SimpleSingleUseCase<Result>: SingleUseCase<Unit, Result> {
    operator fun invoke(): Single<Result> {
        return invoke(Unit)
    }
}

interface FlowableUseCase<Params, Result>: UseCase<Params, Flowable<Result>>

interface SimpleFlowableUseCase<Result>: FlowableUseCase<Unit, Result> {
    operator fun invoke(): Flowable<Result> {
        return invoke(Unit)
    }
}

interface CompletableUseCase<Params>: UseCase<Params, Completable>