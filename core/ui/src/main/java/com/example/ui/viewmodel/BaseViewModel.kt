package com.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ui.livedata.LiveEvent
import com.example.ui.livedata.MutableLiveEvent
import com.example.ui.navigation.NavigationCommand
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewModel<State : Any, Reducer : StateReducer<State>> constructor(
    val reducer: Reducer
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val state: LiveData<State> get() = reducer.stateObservable.toLiveData()

    private val _navigation: MutableLiveEvent<NavigationCommand> = MutableLiveEvent()
    val navigation: LiveEvent<NavigationCommand> get() = _navigation

    protected val _state: State get() = reducer.state

    protected val intentSubject = BehaviorSubject.create<Any>()

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
        _navigation.event = command
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected inline fun <reified T : Any> intentOf(): Observable<T> = intentSubject.ofType(T::class.java)

    protected fun <T> Observable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun <T> Flowable<T>.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) =
        compositeDisposable.add(this.subscribe(block, onError))

    protected fun Completable.subscribeTillClear(onError: (Throwable) -> Unit = {}, block: (() -> Unit) = {}) =
        compositeDisposable.add(this.subscribe({ block() }, { onError(it) }))

    private fun <T> Observable<T>.toLiveData(): LiveData<T> {

        return object : MutableLiveData<T>() {
            var disposable: Disposable? = null

            override fun onActive() {
                super.onActive()

                // Observable -> LiveData
                disposable = this@toLiveData.subscribe {
                    this.postValue(it)
                }
            }

            override fun onInactive() {
                disposable?.dispose()
                super.onInactive()
            }
        }
    }
}