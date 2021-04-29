package com.example.feature.browse.photo.impl.ui.dummy

import com.example.ui.viewmodel.statereducer.StateReducer
import javax.inject.Inject

internal class DummyReducer @Inject constructor(): StateReducer<DummyContract.DummyState>(DummyContract.DummyState.EMPTY) {
    fun setPhrase(phrase: String)  {
        commit { state ->
            state.copy(phrase = phrase)
        }
    }
}