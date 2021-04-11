package com.example.feature.make.photo.impl.di

import com.example.core.flow.di.scope.FlowScope
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.repositories.api.photo.PhotoRepository
import dagger.Component

@FlowScope
@Component(
    modules = [
        MakePhotoFlowModule::class
    ],
    dependencies = [
        PhotoRepository.ComponentInjector::class
    ]
)
interface MakePhotoFlowComponent : MakePhotoApi {

    @Component.Factory
    interface Factory {
        fun create(
            photoRepository: PhotoRepository.ComponentInjector
        ): MakePhotoFlowComponent
    }
}