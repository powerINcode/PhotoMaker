package com.example.repositories.impl.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.repositories.impl.database.entities.PhotoDbo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photo")
    fun observeAllPhotos(): Flowable<List<PhotoDbo>>

    @Insert(onConflict = REPLACE)
    fun insert(photoDbo: PhotoDbo): Completable
}