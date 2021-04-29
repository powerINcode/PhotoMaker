package com.example.ui.mvi.view

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.core.sreams.toMainThread
import com.example.ui.navigation.Navigator
import com.example.ui.viewmodel.BaseViewModel
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class MviViewImpl<Component : Any, State : Any, VM : BaseViewModel<State, *>>: MviView<Component, State, VM> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val renderSubject: PublishSubject<StateReducer.StateChange<State>> = PublishSubject.create()
    private lateinit var viewModelStoreOwner: ViewModelStoreOwner

    private lateinit var viewModelClass: Class<VM>

    lateinit var navigator: Navigator

    lateinit var modelFactory: ViewModelProvider.Factory

    override lateinit var component: Component

    override val viewModel: VM by lazy { ViewModelProvider(viewModelStoreOwner, modelFactory).get(viewModelClass) }

    override fun observeStateChange(): Observable<StateReducer.StateChange<State>> = renderSubject

    override fun onCreateMviView(buildComponentBlock: () -> Component, initializeComponentBlock: (Component) -> Unit) {
        component = buildComponentBlock()
        initializeComponentBlock(component)
    }

    override fun initialize(
        viewModelClass: Class<VM>,
        viewModelStoreOwner: ViewModelStoreOwner,
        navigator: Navigator,
        modelFactory: ViewModelProvider.Factory
    ) {
        this.viewModelClass = viewModelClass
        this.viewModelStoreOwner = viewModelStoreOwner
        this.navigator = navigator
        this.modelFactory = modelFactory

        viewModel.navigation
            .toMainThread()
            .subscribeTillAttach {
                navigator.navigate(it)
            }

        viewModel.state
            .toMainThread()
            .subscribeTillAttach { stateChange ->
                renderSubject.onNext(stateChange)
            }

        viewModel.init()
    }

    override fun onDestroyMviView() {
        compositeDisposable.clear()
    }

    override fun <T> Observable<T>.subscribeTillAttach(block: (T) -> Unit) {
        compositeDisposable.add(this.subscribe(block, {
            Log.e("BaseActivity", "Error happen in the subscribeTillDestroy: ${it.message}")
        }, {}))
    }
}