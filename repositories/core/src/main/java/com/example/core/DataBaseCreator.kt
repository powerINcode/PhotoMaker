package com.example.core

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

object DataBaseCreator {
    fun <T: RoomDatabase> create(application: Application, dbClass: Class<T>, name: String): T {
        return Room.databaseBuilder(
            application,
            dbClass,
            name
        ).build()
    }
}