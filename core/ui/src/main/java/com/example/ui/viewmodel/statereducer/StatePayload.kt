package com.example.ui.viewmodel.statereducer

interface StatePayload<T: Any, P: Any> {
    val empty: P
    fun get(payload: Any?): P
    fun calculate(oldState: T): Any
}