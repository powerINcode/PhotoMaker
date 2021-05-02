package com.example.core.sreams

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Switch thread to [Schedulers.io]
 */
fun Completable.onIo(): Completable = this.subscribeOn(Schedulers.io())