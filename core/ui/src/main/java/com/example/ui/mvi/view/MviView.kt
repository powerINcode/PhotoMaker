package com.example.ui.mvi.view

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ui.navigation.Navigator
import com.example.ui.viewmodel.BaseViewModel
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Observable

interface MviView<Component : Any, State : Any, VM : BaseViewModel<State, *>> {
    val viewModel: VM
    var component: Component

    fun observeStateChange(): Observable<StateReducer.StateChange<State>>

    fun onCreateMviView(buildComponentBlock: () -> Component, initializeComponentBlock: (Component) -> Unit)

    fun initialize(
        viewModelClass: Class<VM>,
        viewModelStoreOwner: ViewModelStoreOwner,
        navigator: Navigator,
        modelFactory: ViewModelProvider.Factory
    )

    fun onDestroyMviView()

    fun <T> Observable<T>.subscribeTillAttach(block: (T) -> Unit)
}