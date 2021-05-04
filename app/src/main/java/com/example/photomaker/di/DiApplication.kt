package com.example.photomaker.di

import android.app.Application
import com.example.feature.browse.photo.impl.di.DaggerBrowsePhotoFlowComponent
import com.example.feature.make.photo.impl.di.DaggerMakePhotoFlowComponent
import com.example.feature.photo.gallery.impl.di.DaggerPhotoGalleryFlowComponent
import com.example.impl.di.DaggerPhotoRepositoryComponent
import com.example.repositories.api.photo.RepositoriesApi
import com.example.repositories.impl.di.DaggerRepositoryComponent

class DiApplication(private val application: Application) {

    private val repositoriesApi: RepositoriesApi by unsafeLazy {
        DaggerRepositoryComponent.factory().create(
            application = application,
            photoRepositoryApi = DaggerPhotoRepositoryComponent.factory().create(application)
        )
    }

    private val makePhotoFeature by unsafeLazy {
        DaggerMakePhotoFlowComponent.factory().create(
            photoRepositoryApi = repositoriesApi
        )
    }

    private val photoGalleryFeature by unsafeLazy {
        DaggerPhotoGalleryFlowComponent.factory().create(repositoriesApi)
    }

    private val browsePhotoFeature by unsafeLazy {
        DaggerBrowsePhotoFlowComponent.factory().create(
            photoRepositoryApi = repositoriesApi
        )
    }

    fun build(): ApplicationComponent {
       return DaggerApplicationComponent.factory().create(
            repositoriesApi = repositoriesApi,
            makePhotoApi = makePhotoFeature,
            photoGalleryApi = photoGalleryFeature,
            browsePhotoApi = browsePhotoFeature
        )
    }

    private fun <T: Any> unsafeLazy(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }
}