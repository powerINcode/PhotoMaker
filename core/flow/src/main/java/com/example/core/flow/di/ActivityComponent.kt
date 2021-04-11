package com.example.core.flow.di

interface ActivityComponent<T> {
    fun inject(target: T)
}