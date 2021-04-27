package com.example.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.example.ui.navigation.NavigationCommand
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<State : Any, Reducer : StateReducer<State>> constructor(
    val reducer: Reducer
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val state: Observable<StateReducer.StateChange<State>> get() = reducer.observeStateChange()

    @VisibleForTesting
    val navigationSubject: PublishSubject<NavigationCommand> = PublishSubject.create()
    val navigation: Observable<NavigationCommand> get() = navigationSubject

    protected val _state: Single<State> get() = reducer.getState()

    protected val intentSubject: BehaviorSubject<Any> = BehaviorSubject.create()

    private val inited: AtomicBoolean = AtomicBoolean(false)

    fun init() {
        if (inited.compareAndSet(false, true)) {
            doInit()
        }
    }

    protected abstract fun doInit()

    fun send(intent: Any) {
        intentSubject.onNext(intent)
    }

    fun navigate(command: NavigationCommand) {
        navigationSubject.onNext(command)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected inline fun <reified T : Any> intentOf(): Observable<T> = intentSubject.ofType(T::class.java)

    protected fun <T> Observable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun <T> Single<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun <T> Flowable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun Completable.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: (() -> Unit) = {}) =
        compositeDisposable.add(this.subscribe({ block() }, { onError(it) }))
}