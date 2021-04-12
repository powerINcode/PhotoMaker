package com.example.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
 * Core interface describe use case structure
 * @param Params input
 * @param Result output
 */
interface UseCase<Params, Result> {
    operator fun invoke(params: Params): Result
}

/**
 * UseCase adopt for the [Single] task
 * @param Params input
 * @param Result output
 */
interface SingleUseCase<Params, Result>: UseCase<Params, Single<Result>>

/**
 * UseCase adopt for the [Single] task without input data
 * @param Result output
 */
interface SimpleSingleUseCase<Result>: SingleUseCase<Unit, Result> {
    operator fun invoke(): Single<Result> {
        return invoke(Unit)
    }
}

/**
 * UseCase adopt for the [Flowable] task
 * @param Params input
 * @param Result output
 */
interface FlowableUseCase<Params, Result>: UseCase<Params, Flowable<Result>>

/**
 * UseCase adopt for the [Flowable] task without input data
 * @param Result output
 */
interface SimpleFlowableUseCase<Result>: FlowableUseCase<Unit, Result> {
    operator fun invoke(): Flowable<Result> {
        return invoke(Unit)
    }
}

/**
 * UseCase adopt for the [Completable] task without output data
 * @param Params input
 */
interface CompletableUseCase<Params>: UseCase<Params, Completable>