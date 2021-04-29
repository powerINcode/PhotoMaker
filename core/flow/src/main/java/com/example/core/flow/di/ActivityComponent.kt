package com.example.core.flow.di

import androidx.appcompat.app.AppCompatActivity

interface ActivityComponent<T: AppCompatActivity> {
    fun inject(target: T)
}