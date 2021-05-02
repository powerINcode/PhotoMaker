package com.example.core.sreams

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*

/**
 * Convert [Observable] to the [Flowable] with [BackpressureStrategy.DROP] that allow to
 * not interrupt already running task
 * @param block [CompletableSource] with task
 */
fun <T> Observable<T>.flatMapDropCompletable(block: (T) -> CompletableSource): Completable = this.toFlowable(BackpressureStrategy.DROP)
    .flatMapCompletable(block, false, 1)

/**
 * Switch thread to [AndroidSchedulers.mainThread]
 */
fun <T> Observable<T>.toMainThread(): Observable<T> = this.observeOn(AndroidSchedulers.mainThread())

/**
 * Map [Observable] to [Unit]
 */
fun <T: Any> Observable<T>.toUnit() = this.map {  }