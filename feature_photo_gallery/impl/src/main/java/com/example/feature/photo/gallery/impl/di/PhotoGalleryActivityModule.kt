package com.example.feature.photo.gallery.impl.di

import androidx.lifecycle.ViewModel
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryViewModel
import com.example.ui.activity.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface PhotoGalleryActivityModule {

    @ActivityScope
    @IntoMap
    @ViewModelKey(PhotoGalleryViewModel::class)
    @Binds
    fun bindsPhotoGalleryViewModel(vm: PhotoGalleryViewModel): ViewModel
}