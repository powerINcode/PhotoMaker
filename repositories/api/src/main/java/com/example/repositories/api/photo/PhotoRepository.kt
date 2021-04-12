package com.example.repositories.api.photo

import com.example.core.flow.FlowEntity
import com.example.repositories.api.photo.entities.Photo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Repository describe photo collection of the app
 */
interface PhotoRepository: FlowEntity {

    fun observePhotos(): Flowable<List<Photo>>

    fun savePhoto(name: String, path: String): Completable

    interface ComponentInjector {
        fun getPhotoRepository(): PhotoRepository
    }
}