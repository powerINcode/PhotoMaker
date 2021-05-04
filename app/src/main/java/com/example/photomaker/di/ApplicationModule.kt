package com.example.photomaker.di

import com.example.core.flow.FlowEntity
import com.example.feature.browse.photo.api.BrowsePhotoApi
import com.example.feature.photo.gallery.api.PhotoGalleryApi
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.photomaker.di.keys.FlowModuleKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @Provides
    @IntoMap
    @FlowModuleKey(MakePhotoApi::class)
    fun provideMakePhotoApi(flow: MakePhotoApi): FlowEntity = flow

    @Singleton
    @Provides
    @IntoMap
    @FlowModuleKey(PhotoGalleryApi::class)
    fun providePhotoGalleryApi(flow: PhotoGalleryApi): FlowEntity = flow

    @Singleton
    @Provides
    @IntoMap
    @FlowModuleKey(BrowsePhotoApi::class)
    fun provideBrowsePhotoApi(flow: BrowsePhotoApi): FlowEntity = flow
}