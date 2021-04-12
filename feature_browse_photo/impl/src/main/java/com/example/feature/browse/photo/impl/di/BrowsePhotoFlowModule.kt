package com.example.feature.browse.photo.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature.browse.photo.api.domain.GetPhotoByIdUseCase
import com.example.feature.browse.photo.impl.domain.GetPhotoByIdUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface BrowsePhotoFlowModule {
    @FlowScope
    @Binds
    fun bindGetPhotoByIdUseCase(useCase: GetPhotoByIdUseCaseImpl): GetPhotoByIdUseCase
}