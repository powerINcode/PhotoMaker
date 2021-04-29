package com.example.feature.browse.photo.impl.ui.dummy.di

import androidx.lifecycle.ViewModel
import com.example.core.flow.di.scope.FragmentScope
import com.example.feature.browse.photo.impl.ui.dummy.DummyViewModel
import com.example.ui.activity.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface DummyFragmentModule {

    @FragmentScope
    @IntoMap
    @ViewModelKey(DummyViewModel::class)
    @Binds
    fun bindsDummyViewModel(vm: DummyViewModel): ViewModel
}