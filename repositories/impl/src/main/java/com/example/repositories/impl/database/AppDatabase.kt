package com.example.repositories.impl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repositories.impl.database.daos.PhotoDao
import com.example.repositories.impl.database.entities.PhotoDbo

@Database(entities = [PhotoDbo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}