package com.example.impl.di

import android.app.Application
import com.example.api.PhotoRepositoryApi
import com.example.core.flow.di.scope.FlowScope
import dagger.BindsInstance
import dagger.Component

@FlowScope
@Component(
    modules = [
        PhotoRepositoryModule::class
    ]
)
interface PhotoRepositoryComponent: PhotoRepositoryApi {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): PhotoRepositoryComponent
    }
}