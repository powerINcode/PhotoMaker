package com.example.repositories.impl.di

import android.app.Application
import androidx.room.Room
import com.example.core.flow.di.scope.FlowScope
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.impl.database.AppDatabase
import com.example.repositories.impl.database.daos.PhotoDao
import com.example.repositories.impl.photo.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface RepositoryModule {

    @FlowScope
    @Binds
    fun bindPhotoRepository(repository: PhotoRepositoryImpl): PhotoRepository

    companion object {
        @FlowScope
        @Provides
        fun provideAppDatabase(application: Application): AppDatabase = Room.databaseBuilder(
            application,
            AppDatabase::class.java, "photo_maker_db"
        ).build()

        @FlowScope
        @Provides
        fun providePhotoDao(db: AppDatabase): PhotoDao = db.photoDao()
    }
}