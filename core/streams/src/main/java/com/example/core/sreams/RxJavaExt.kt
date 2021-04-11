package com.example.core.sreams

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.flatMapDropCompletable(block: (T) -> CompletableSource): Completable = this.toFlowable(BackpressureStrategy.DROP)
    .flatMapCompletable(block, false, 1)

fun <T> Observable<T>.toMainThread(): Observable<T> = this.observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.onIo(): Flowable<T> = this.subscribeOn(Schedulers.io())
fun <T> Flowable<T>.toMainThread(): Flowable<T> = this.observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.onIo(): Single<T> = this.subscribeOn(Schedulers.io())
fun <T> Single<T>.toMainThread(): Single<T> = this.observeOn(AndroidSchedulers.mainThread())

fun Completable.onIo(): Completable = this.subscribeOn(Schedulers.io())