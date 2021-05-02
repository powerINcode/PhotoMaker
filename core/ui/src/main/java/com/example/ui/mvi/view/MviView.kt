package com.example.ui.mvi.view

import androidx.lifecycle.ViewModel
import com.example.ui.mvi.presenter.Presenter
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Observable

interface MviView<Component : Any, State : Any, P: Presenter> {
    var component: Component

    fun observeStateChange(): Observable<StateReducer.StateChange<State>>

    fun initialize(
        viewModel: ViewModel,
        presenter: Presenter
    )

    fun onCreateMviView(buildComponentBlock: () -> Component, initializeComponentBlock: (Component) -> Unit)

    fun onAttachMviView()

    fun onDetachMviView()

    fun onDestroyMviView()

    fun <T> Observable<T>.subscribeTillDestroy(block: (T) -> Unit)
}