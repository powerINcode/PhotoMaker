package com.example.impl

import com.example.api.PhotoRepository
import com.example.api.entities.Photo
import com.example.core.sreams.onIo
import com.example.impl.database.daos.PhotoDao
import com.example.impl.database.entities.PhotoDbo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.joda.time.Instant
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val mapper: PhotoRepositoryMapper
) : PhotoRepository {
    override fun savePhoto(name: String, path: String): Completable = photoDao.insert(
        PhotoDbo(
            name = name,
            path = path,
            createdAt = Instant.now().millis
        )
    )
        .onIo()

    override fun getPhoto(id: Long): Single<Photo> = photoDao.getPhoto(id)
        .map { dbo -> mapper.map(dbo) }
        .onIo()

    override fun observePhotos(): Flowable<List<Photo>> = photoDao.observeAllPhotos()
        .map { photos -> photos.map(mapper::map) }
        .onIo()
}