package com.example.photomaker.di

import com.example.core.flow.FlowEntity
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.repositories.api.RepositoriesApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class
    ],
    dependencies = [
        RepositoriesApi::class,
        MakePhotoApi::class
    ]
)
interface ApplicationComponent {

    fun getApplicationFlowEntities(): Map<Class<out FlowEntity>, @JvmSuppressWildcards FlowEntity>

    @Component.Factory
    interface Factory {
        fun create(
            repositoriesApi: RepositoriesApi,
            makePhotoApi: MakePhotoApi
        ): ApplicationComponent
    }
}