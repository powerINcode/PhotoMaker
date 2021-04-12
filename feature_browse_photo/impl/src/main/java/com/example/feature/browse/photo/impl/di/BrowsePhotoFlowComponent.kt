package com.example.feature.browse.photo.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature.browse.photo.api.BrowsePhotoApi
import com.example.repositories.api.photo.PhotoRepository
import dagger.Component

@FlowScope
@Component(
    modules = [
        BrowsePhotoFlowModule::class
    ],
    dependencies = [
        PhotoRepository.ComponentInjector::class
    ]
)
interface BrowsePhotoFlowComponent : BrowsePhotoApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepository: PhotoRepository.ComponentInjector
        ): BrowsePhotoFlowComponent
    }
}