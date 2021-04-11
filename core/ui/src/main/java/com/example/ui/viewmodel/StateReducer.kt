package com.example.ui.viewmodel

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class StateReducer<State : Any>(initialState: State) {
    private val _stateSubject: BehaviorSubject<State> = BehaviorSubject.createDefault(initialState)
    private val serializedSubject: Subject<State> = _stateSubject.toSerialized()
    val stateObservable: Observable<State> = _stateSubject
    private val lock = Object()

    val state: State
        get() = _stateSubject.value

    protected fun State.commit() {
        serializedSubject.onNext(this)
    }
}