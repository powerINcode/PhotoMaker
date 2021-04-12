package com.example.feature.browse.photo.impl.di

import androidx.lifecycle.ViewModel
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.browse.photo.impl.ui.BrowsePhotoViewModel
import com.example.ui.activity.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface BrowsePhotoActivityModule {

    @ActivityScope
    @IntoMap
    @ViewModelKey(BrowsePhotoViewModel::class)
    @Binds
    fun bindsBrowsePhotoViewModel(vm: BrowsePhotoViewModel): ViewModel
}