package com.example.impl.di

import android.app.Application
import com.example.api.PhotoRepository
import com.example.core.DataBaseCreator
import com.example.core.flow.di.scope.FlowScope
import com.example.impl.PhotoRepositoryImpl
import com.example.impl.database.PhotoDatabase
import com.example.impl.database.daos.PhotoDao
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface PhotoRepositoryModule {

    @FlowScope
    @Binds
    fun bindPhotoRepository(repository: PhotoRepositoryImpl): PhotoRepository

    companion object {
        @FlowScope
        @Provides
        fun provideAppDatabase(application: Application): PhotoDatabase = DataBaseCreator.create(
            application = application,
            dbClass = PhotoDatabase::class.java,
            name = "photo_maker_db"
        )

        @FlowScope
        @Provides
        fun providePhotoDao(db: PhotoDatabase): PhotoDao = db.photoDao()
    }
}