package com.example.photomaker.di

import com.example.core.flow.FlowEntity
import com.example.feature_make_photo.api.MakePhotoApi
import com.example.photomaker.di.keys.FlowModuleKey
import com.example.repositories.api.RepositoriesApi
import com.example.repositories.api.photo.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Singleton
    @Provides
    @IntoMap
    @FlowModuleKey(PhotoRepository::class)
    fun providePhotoRepositoryComponentInjector(repositoriesApi: RepositoriesApi): FlowEntity = repositoriesApi.getPhotoRepository()

    @Singleton
    @Provides
    @IntoMap
    @FlowModuleKey(MakePhotoApi::class)
    fun provideMakePhotoApi(flow: MakePhotoApi): FlowEntity = flow
}