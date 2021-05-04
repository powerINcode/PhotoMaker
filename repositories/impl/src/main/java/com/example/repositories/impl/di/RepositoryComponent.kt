package com.example.repositories.impl.di

import android.app.Application
import com.example.api.PhotoRepositoryApi
import com.example.core.flow.di.scope.FlowScope
import com.example.repositories.api.photo.RepositoriesApi
import dagger.BindsInstance
import dagger.Component

@FlowScope
@Component(
    modules = [
        RepositoryModule::class
    ],
    dependencies = [
        PhotoRepositoryApi::class
    ]
)
interface RepositoryComponent : RepositoriesApi {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            photoRepositoryApi: PhotoRepositoryApi
        ): RepositoryComponent
    }
}