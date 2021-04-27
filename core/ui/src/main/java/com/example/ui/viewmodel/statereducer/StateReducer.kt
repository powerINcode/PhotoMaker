package com.example.ui.viewmodel.statereducer

import androidx.annotation.MainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class StateReducer<State : Any>(initialState: State) {
    private val _stateChangeSubject: Subject<(State) -> State> = BehaviorSubject.create<(State) -> State>().toSerialized()

    private val _stateSubject: Subject<StateChange<State>> = BehaviorSubject.createDefault(StateChange(initialState, null)).toSerialized()

    @Suppress("UNCHECKED_CAST")
    fun observeStateChange(): Observable<StateChange<State>> {
        return _stateSubject.firstOrError()
            .flatMapObservable { lastStateChange ->
                _stateChangeSubject
                    .scan(lastStateChange, { stateChange, change ->
                        val oldState = stateChange.state
                        val newState = change(oldState)
                        val payload = (newState as? StatePayload<State, *>)?.let { newState.calculate(oldState) }
                        StateChange(newState, payload)
                    })
                    .doOnNext { stateChange ->
                        _stateSubject.onNext(stateChange)
                        // reset payload
                        (stateChange.state as? StatePayload<State, *>)?.let {
                            _stateSubject.onNext(stateChange.copy(payload = it.calculate(stateChange.state)))
                        }
                    }
                    .startWithItem(StateChange(lastStateChange.state, null))
            }
            .distinctUntilChanged()
    }

    fun getState(): Single<State> = _stateSubject.firstOrError().map { stateChange -> stateChange.state }

    @MainThread
    protected fun commit(block: (State) -> State) {
        _stateChangeSubject.onNext(block)
    }

    data class StateChange<T : Any>(
        val state: T,
        val payload: Any?
    )
}