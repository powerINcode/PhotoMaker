package com.example.photomaker

import android.app.Application
import com.example.core.flow.FlowEntity
import com.example.core.flow.FlowEntityProvider
import com.example.feature.make.photo.impl.di.DaggerMakePhotoFlowComponent
import com.example.photomaker.di.ApplicationComponent
import com.example.photomaker.di.DaggerApplicationComponent
import com.example.repositories.impl.di.DaggerRepositoryComponent

class PhotoMakerApplication: Application(), FlowEntityProvider {

    private lateinit var applicationComponent: ApplicationComponent

    private val flowEntities by lazy(LazyThreadSafetyMode.NONE) {
        applicationComponent.getApplicationFlowEntities()
    }

    override fun onCreate() {
        super.onCreate()


        val repositoriesApi = DaggerRepositoryComponent.factory().create(application = this)
        applicationComponent = DaggerApplicationComponent.factory().create(
            repositoriesApi = repositoriesApi,
            makePhotoApi = DaggerMakePhotoFlowComponent.factory().create(
                photoRepository = repositoriesApi
            )
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : FlowEntity> getFlow(flow: Class<T>): T {
        return flowEntities[flow] as? T ?: throw IllegalStateException("Unsupported flow entity: $flow")
    }
}