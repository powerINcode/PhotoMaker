package com.example.core.sreams

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Switch thread to [Schedulers.io]
 */
fun <T> Single<T>.onIo(): Single<T> = this.subscribeOn(Schedulers.io())

/**
 * Switch thread to [AndroidSchedulers.mainThread]
 */
fun <T> Single<T>.toMainThread(): Single<T> = this.observeOn(AndroidSchedulers.mainThread())