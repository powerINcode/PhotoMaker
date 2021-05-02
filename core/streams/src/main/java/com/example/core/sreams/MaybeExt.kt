package com.example.core.sreams

import io.reactivex.rxjava3.core.Maybe

/**
 * Map [Maybe] to [Unit]
 */
fun <T: Any> Maybe<T>.toUnit() = this.map {  }