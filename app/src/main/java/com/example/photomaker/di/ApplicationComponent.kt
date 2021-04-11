package com.example.photomaker.di

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig
import com.example.core.flow.FlowEntity
import com.example.feature.photo.gallery.api.PhotoGalleryApi
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.repositories.api.RepositoriesApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NavigationModule::class
    ],
    dependencies = [
        RepositoriesApi::class,
        MakePhotoApi::class,
        PhotoGalleryApi::class
    ]
)
interface ApplicationComponent {

    fun getApplicationFlowEntities(): Map<Class<out FlowEntity>, @JvmSuppressWildcards FlowEntity>
    fun getApplicationNavigation(): Map<Class<out FlowConfig>, @JvmSuppressWildcards Class<out AppCompatActivity>>

    @Component.Factory
    interface Factory {
        fun create(
            repositoriesApi: RepositoriesApi,
            makePhotoApi: MakePhotoApi,
            photoGalleryApi: PhotoGalleryApi
        ): ApplicationComponent
    }
}