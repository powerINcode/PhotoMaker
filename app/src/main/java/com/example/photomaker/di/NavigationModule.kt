package com.example.photomaker.di

import androidx.appcompat.app.AppCompatActivity
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.impl.ui.BrowsePhotoActivity
import com.example.feature.make.photo.impl.ui.MakePhotoActivity
import com.example.feature_make_photo.api.MakePhotoFlowConfig
import com.example.photomaker.di.keys.NavigationModuleKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object NavigationModule {

    @Singleton
    @Provides
    @IntoMap
    @NavigationModuleKey(MakePhotoFlowConfig::class)
    fun provideMakePhotoFlowActivity(): Class<out AppCompatActivity> = MakePhotoActivity::class.java

    @Singleton
    @Provides
    @IntoMap
    @NavigationModuleKey(BrowsePhotoFlowConfig::class)
    fun provideBrowsePhotoActivity(): Class<out AppCompatActivity> = BrowsePhotoActivity::class.java
}