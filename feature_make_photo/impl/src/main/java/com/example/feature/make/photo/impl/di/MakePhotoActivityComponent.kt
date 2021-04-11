package com.example.feature.make.photo.impl.di

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.di.ActivityComponent
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.make.photo.impl.ui.MakePhotoActivity
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.ui.activity.di.BaseActivityModule
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [
        BaseActivityModule::class,
        MakePhotoActivityModule::class
    ],
    dependencies = [
        MakePhotoApi::class
    ]
)
interface MakePhotoActivityComponent: ActivityComponent<MakePhotoActivity> {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: AppCompatActivity,
            makePhotoApi: MakePhotoApi
        ): MakePhotoActivityComponent
    }
}