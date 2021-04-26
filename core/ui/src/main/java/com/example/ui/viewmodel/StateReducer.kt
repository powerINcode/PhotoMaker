package com.example.ui.viewmodel

import androidx.annotation.MainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class StateReducer<State : Any>(initialState: State) {
    private val _stateChangeSubject: Subject<(State) -> State> = BehaviorSubject.create<(State) -> State>().toSerialized()

    private val _stateSubject: Subject<State> = BehaviorSubject.createDefault(initialState).toSerialized()

    val stateObservable: Observable<State> = _stateSubject.firstOrError()
        .flatMapObservable { lastState ->
            _stateChangeSubject
                .scan(lastState, { state, change -> change(state) })
                .doOnNext { _stateSubject.onNext(it) }
        }

    val state: Single<State> get() = _stateSubject.firstOrError()

    @MainThread
    protected fun commit(block: (State) -> State) {
        _stateChangeSubject.onNext(block)
    }
}