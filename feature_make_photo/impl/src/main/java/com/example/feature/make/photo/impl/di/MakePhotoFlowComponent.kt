package com.example.feature.make.photo.impl.di

import com.example.api.PhotoRepositoryApi
import com.example.core.flow.di.scope.FlowScope
import com.example.feature_make_photo.api.MakePhotoApi
import dagger.Component

@FlowScope
@Component(
    modules = [
        MakePhotoFlowModule::class
    ],
    dependencies = [
        PhotoRepositoryApi::class
    ]
)
interface MakePhotoFlowComponent : MakePhotoApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepositoryApi: PhotoRepositoryApi
        ): MakePhotoFlowComponent
    }
}