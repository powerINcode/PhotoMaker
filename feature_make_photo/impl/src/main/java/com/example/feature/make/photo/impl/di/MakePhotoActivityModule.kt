package com.example.feature.make.photo.impl.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.core.flow.di.scope.ActivityScope
import com.example.feature.make.photo.impl.ui.MakePhotoViewModel
import com.example.ui.activity.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal interface MakePhotoActivityModule {

    @ActivityScope
    @IntoMap
    @ViewModelKey(MakePhotoViewModel::class)
    @Binds
    fun bindsMakePhotoViewModel(vm: MakePhotoViewModel): ViewModel

    companion object {
        @Provides
        fun provideApplication(activity: AppCompatActivity): Application = activity.application
    }
}