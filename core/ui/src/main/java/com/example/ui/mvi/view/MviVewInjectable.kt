package com.example.ui.mvi.view

interface MviVewInjectable<Component> {
    fun createComponent(): Component
    fun inject(component: Component)
}