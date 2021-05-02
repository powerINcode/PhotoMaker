package com.example.ui.mvi.presenter

import androidx.lifecycle.ViewModel
import com.example.ui.viewmodel.BaseViewModel
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BasePresenter<State : Any, VM: BaseViewModel<State, *>> : Presenter {

    private val attachSubject: PublishSubject<Unit> = PublishSubject.create()

    protected val intentSubject: PublishSubject<Any> = PublishSubject.create()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(vm: ViewModel) {
        viewModel = (vm as? VM) ?: return
    }

    override fun onCreated() {
        viewModel.init()
    }

    override fun onAttach() {
        attachSubject.onNext(Unit)
    }

    override fun onDetach() {

    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun send(intent: Any) {
        intentSubject.onNext(intent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <State : Any> observeStateChange(): Observable<StateReducer.StateChange<State>> {
        return viewModel.observeStateChange() as Observable<StateReducer.StateChange<State>>
    }

    protected fun attachObservable(): Observable<Unit> = attachSubject

    protected inline fun <reified T : Any> intentOf(): Observable<T> = intentSubject.ofType(T::class.java)

    protected fun <T> Observable<T>.subscribeTillDestroy(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) = compositeDisposable.add(this.subscribe(block, onError))
    protected fun <T> Maybe<T>.subscribeTillDestroy(onError: (Throwable) -> Unit = {}, block: ((T) -> Unit) = {}) = compositeDisposable.add(this.subscribe(block, onError))
    protected fun Completable.subscribeTillDestroy(onError: (Throwable) -> Unit = {}, block: (() -> Unit) = {}) = compositeDisposable.add(
        this.subscribe({
            block()
        }, {
            onError(it)
        })
    )
}