package com.example.api

import com.example.core.flow.FlowEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
 * Repository describe photo collection of the app
 */
interface PhotoRepository: FlowEntity {

    fun getPhoto(id: Long): Single<com.example.api.entities.Photo>

    fun observePhotos(): Flowable<List<com.example.api.entities.Photo>>

    fun savePhoto(name: String, path: String): Completable
}