package com.example.feature.photo.gallery.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature.photo.gallery.api.domain.ObservePhotosUseCase
import com.example.feature.photo.gallery.impl.domain.ObservePhotosUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface PhotoGalleryFlowModule {
    @FlowScope
    @Binds
    fun bindObservePhotosUseCase(useCase: ObservePhotosUseCaseImpl): ObservePhotosUseCase
}