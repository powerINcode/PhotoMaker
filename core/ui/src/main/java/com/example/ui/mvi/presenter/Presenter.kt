package com.example.ui.mvi.presenter

import androidx.lifecycle.ViewModel
import com.example.ui.viewmodel.statereducer.StateReducer
import io.reactivex.rxjava3.core.Observable

/**
 * Presenter interface
 */
interface Presenter {

    /**
     * Calls when initialize presenter
     */
    fun onCreate(vm: ViewModel)

    /**
     * Calls when presenter created
     */
    fun onCreated()

    /**
     * Calls when presenter attached (onResume)
     */
    fun onAttach()

    /**
     * Calls when presenter detached (onPause)
     */
    fun onDetach()


    /**
     * Calls when presenter destroy (OnDestroy)
     */
    fun onDestroy()

    fun<State: Any> observeStateChange(): Observable<StateReducer.StateChange<State>>
}