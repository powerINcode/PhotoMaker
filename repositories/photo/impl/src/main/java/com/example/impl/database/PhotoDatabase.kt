package com.example.impl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.impl.database.daos.PhotoDao
import com.example.impl.database.entities.PhotoDbo

@Database(entities = [PhotoDbo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}