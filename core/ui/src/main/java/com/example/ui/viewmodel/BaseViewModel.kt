package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<State : Any, Reducer : StateReducer<State>> constructor(
    val reducer: Reducer
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun observeStateChange(): Observable<StateReducer.StateChange<State>> = reducer.observeStateChange()

    private val inited: AtomicBoolean = AtomicBoolean(false)

    fun init() {
        if (inited.compareAndSet(false, true)) {
            doInit()
        }
    }

    protected abstract fun doInit()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun <T> Observable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun <T> Single<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun <T> Flowable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun Completable.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: (() -> Unit) = {}) =
        compositeDisposable.add(this.subscribe({ block() }, { onError(it) }))
}