package com.example.repositories.impl.photo

import com.example.core.sreams.onIo
import com.example.repositories.api.photo.PhotoRepository
import com.example.repositories.api.photo.entities.Photo
import com.example.repositories.impl.database.daos.PhotoDao
import com.example.repositories.impl.database.entities.PhotoDbo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import org.joda.time.Instant
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao,
    private val mapper: PhotoRepositoryMapper
): PhotoRepository {
    override fun savePhoto(name: String, path: String): Completable = photoDao.insert(PhotoDbo(
        name = name,
        path = path,
        createdAt = Instant.now().millis
    ))
        .onIo()

    override fun observePhotos(): Flowable<List<Photo>> = photoDao.observeAllPhotos().map { photos ->
        photos.map(mapper::map)
    }
        .onIo()
}