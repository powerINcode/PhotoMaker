package com.example.feature.browse.photo.impl.di

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.di.ActivityComponent
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.browse.photo.api.BrowsePhotoApi
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.impl.ui.BrowsePhotoActivity
import com.example.ui.activity.di.BaseActivityModule
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [
        BaseActivityModule::class,
        BrowsePhotoActivityModule::class
    ],
    dependencies = [
        BrowsePhotoApi::class
    ]
)
internal interface BrowsePhotoActivityComponent : ActivityComponent<BrowsePhotoActivity> {
    @Component.Factory
    interface Factory {
        fun create(
            browsePhotoApi: BrowsePhotoApi,
            @BindsInstance activity: AppCompatActivity,
            @BindsInstance configuration: BrowsePhotoFlowConfig,
        ): BrowsePhotoActivityComponent
    }
}