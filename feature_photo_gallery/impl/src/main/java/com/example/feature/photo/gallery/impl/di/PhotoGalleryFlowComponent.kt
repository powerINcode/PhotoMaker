package com.example.feature.photo.gallery.impl.di

import com.example.api.PhotoRepositoryApi
import com.example.core.flow.di.scope.FlowScope
import com.example.feature.photo.gallery.api.PhotoGalleryApi
import dagger.Component

@FlowScope
@Component(
    modules = [
        PhotoGalleryFlowModule::class
    ],
    dependencies = [
        PhotoRepositoryApi::class
    ]
)
interface PhotoGalleryFlowComponent : PhotoGalleryApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepositoryApi: PhotoRepositoryApi
        ): PhotoGalleryFlowComponent
    }
}