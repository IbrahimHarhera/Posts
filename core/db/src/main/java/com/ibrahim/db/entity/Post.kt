package com.ibrahim.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val title: String?,
    val body: String?,
    val realId: Int?
)