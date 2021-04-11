package com.example.feature.make.photo.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature.make.photo.impl.domain.SavePhotoPathUseCaseImpl
import com.example.feature_make_photo.api.domain.SavePhotoPathUseCase
import dagger.Binds
import dagger.Module

@Module
internal interface MakePhotoFlowModule {
    @FlowScope
    @Binds
    fun bindSavePhotoPathUseCase(useCase: SavePhotoPathUseCaseImpl): SavePhotoPathUseCase
}