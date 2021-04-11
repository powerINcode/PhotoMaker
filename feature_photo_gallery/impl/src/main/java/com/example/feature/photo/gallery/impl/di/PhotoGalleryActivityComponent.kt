package com.example.feature.photo.gallery.impl.di

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.di.ActivityComponent
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.photo.gallery.api.PhotoGalleryApi
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryActivity
import com.example.ui.activity.di.BaseActivityModule
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [
        BaseActivityModule::class,
        PhotoGalleryActivityModule::class
    ],
    dependencies = [
        PhotoGalleryApi::class
    ]
)
interface PhotoGalleryActivityComponent : ActivityComponent<PhotoGalleryActivity> {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: AppCompatActivity,
            photoGalleryApi: PhotoGalleryApi
        ): PhotoGalleryActivityComponent
    }
}