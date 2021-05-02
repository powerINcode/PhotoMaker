package com.example.ui.viewmodel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

object ViewModelModuleProvider {
    fun <T : ViewModel> provide(
        owner: ViewModelStoreOwner,
        modelFactory: ViewModelProvider.Factory,
        vmClass: Class<T>
    ): T = ViewModelProvider(owner, modelFactory).get(vmClass)
}