package com.example.feature.photo.gallery.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature.photo.gallery.api.PhotoGalleryApi
import com.example.repositories.api.photo.PhotoRepository
import dagger.Component

@FlowScope
@Component(
    modules = [
        PhotoGalleryFlowModule::class
    ],
    dependencies = [
        PhotoRepository.ComponentInjector::class
    ]
)
interface PhotoGalleryFlowComponent : PhotoGalleryApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepository: PhotoRepository.ComponentInjector
        ): PhotoGalleryFlowComponent
    }
}