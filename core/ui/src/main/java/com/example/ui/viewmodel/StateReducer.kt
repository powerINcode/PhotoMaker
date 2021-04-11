package com.example.ui.viewmodel

import androidx.annotation.MainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class StateReducer<State : Any>(initialState: State) {
    private val _stateSubject: BehaviorSubject<State> = BehaviorSubject.createDefault(initialState)

    val stateObservable: Observable<State> = _stateSubject

    val state: State get() = _stateSubject.value

    @MainThread
    protected fun State.commit() {
        _stateSubject.onNext(this)
    }
}