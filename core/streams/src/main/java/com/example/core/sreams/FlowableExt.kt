package com.example.core.sreams

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Switch thread to [Schedulers.io]
 */
fun <T> Flowable<T>.onIo(): Flowable<T> = this.subscribeOn(Schedulers.io())

/**
 * Switch thread to [AndroidSchedulers.mainThread]
 */
fun <T> Flowable<T>.toMainThread(): Flowable<T> = this.observeOn(AndroidSchedulers.mainThread())