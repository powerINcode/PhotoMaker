package com.example.ui.mvi.view

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.core.sreams.toMainThread
import com.example.ui.mvi.presenter.Presenter
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class MviViewImpl<Component : Any, State : Any, P : Presenter> : MviView<Component, State, P> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val renderSubject: PublishSubject<StateReducer.StateChange<State>> = PublishSubject.create()

    override lateinit var component: Component

    private var presenter: Presenter? = null

    override fun observeStateChange(): Observable<StateReducer.StateChange<State>> = renderSubject

    override fun initialize(viewModel: ViewModel, presenter: Presenter) {
        this.presenter = presenter

        presenter.onCreate(viewModel)

        presenter.observeStateChange<State>()
            .toMainThread()
            .subscribeTillDestroy { stateChange ->
                renderSubject.onNext(stateChange)
            }

        presenter.onCreated()
    }

    override fun onCreateMviView(buildComponentBlock: () -> Component, initializeComponentBlock: (Component) -> Unit) {
        component = buildComponentBlock()
        initializeComponentBlock(component)
    }

    override fun onAttachMviView() {
        presenter?.onAttach()
    }

    override fun onDetachMviView() {
        presenter?.onDetach()
    }

    override fun onDestroyMviView() {
        compositeDisposable.clear()
        presenter = null
    }

    override fun <T> Observable<T>.subscribeTillDestroy(block: (T) -> Unit) {
        compositeDisposable.add(this.subscribe(block, {
            Log.e("BaseActivity", "Error happen in the subscribeTillDestroy: ${it.message}")
        }, {}))
    }
}