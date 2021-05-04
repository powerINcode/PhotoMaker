package com.example.impl.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class PhotoDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val path: String,
    val createdAt: Long
)