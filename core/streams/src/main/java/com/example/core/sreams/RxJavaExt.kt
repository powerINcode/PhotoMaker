package com.example.core.sreams

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.flatMapDropCompletable(block: (T) -> CompletableSource): Completable = this.toFlowable(BackpressureStrategy.DROP)
    .flatMapCompletable(block, false, 1)

fun <T> Flowable<T>.onIo(): Flowable<T> = this.subscribeOn(Schedulers.io())
fun Completable.onIo(): Completable = this.subscribeOn(Schedulers.io())