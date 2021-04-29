package com.example.core.flow.di

import androidx.fragment.app.Fragment

interface FragmentComponent<T: Fragment> {
    fun inject(target: T)
}