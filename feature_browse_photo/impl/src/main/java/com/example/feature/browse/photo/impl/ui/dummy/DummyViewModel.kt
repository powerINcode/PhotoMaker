package com.example.feature.browse.photo.impl.ui.dummy

import com.example.ui.viewmodel.BaseViewModel
import javax.inject.Inject

internal class DummyViewModel @Inject constructor(
    reducer: DummyReducer,
    private val configuration: DummyContract.Configuration
): BaseViewModel<DummyContract.DummyState, DummyReducer>(reducer) {

    override fun doInit() {
        reducer.setPhrase(configuration.phrase)
    }
}