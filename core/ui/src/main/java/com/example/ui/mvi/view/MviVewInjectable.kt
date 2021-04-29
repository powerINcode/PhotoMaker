package com.example.ui.mvi.view

interface MviVewInjectable<Component, VM> {
    fun getViewModelClass(): Class<VM>
    fun createComponent(): Component
    fun inject(component: Component)
}