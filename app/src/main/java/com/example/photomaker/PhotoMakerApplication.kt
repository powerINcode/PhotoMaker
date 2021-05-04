package com.example.photomaker

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig
import com.example.core.flow.FlowEntity
import com.example.core.flow.FlowEntityProvider
import com.example.photomaker.di.ApplicationComponent
import com.example.photomaker.di.DiApplication
import com.example.ui.navigation.NavigationProvider

class PhotoMakerApplication : Application(), FlowEntityProvider, NavigationProvider {

    private lateinit var applicationComponent: ApplicationComponent

    private val diApplication: DiApplication = DiApplication(this)

    private val flowEntities by lazy(LazyThreadSafetyMode.NONE) {
        applicationComponent.getApplicationFlowEntities()
    }

    private val navigation by lazy(LazyThreadSafetyMode.NONE) {
        applicationComponent.getApplicationNavigation()
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = diApplication.build()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : FlowEntity> getFlow(flow: Class<T>): T {
        return flowEntities[flow] as? T ?: throw IllegalStateException("Unsupported flow entity: $flow")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : FlowConfig> getNavigation(config: Class<T>): Class<AppCompatActivity> {
        return navigation[config] as? Class<AppCompatActivity> ?: throw IllegalStateException("Unsupported navigation config: $config")
    }
}