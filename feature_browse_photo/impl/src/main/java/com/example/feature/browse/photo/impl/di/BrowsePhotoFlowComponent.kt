package com.example.feature.browse.photo.impl.di

import com.example.api.PhotoRepositoryApi
import com.example.core.flow.di.scope.FlowScope
import com.example.feature.browse.photo.api.BrowsePhotoApi
import dagger.Component

@FlowScope
@Component(
    modules = [
        BrowsePhotoFlowModule::class
    ],
    dependencies = [
        PhotoRepositoryApi::class
    ]
)
interface BrowsePhotoFlowComponent : BrowsePhotoApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepositoryApi: PhotoRepositoryApi
        ): BrowsePhotoFlowComponent
    }
}