package com.ibrahim.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibrahim.db.entity.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDB :RoomDatabase(){
    abstract fun postDao():PostDao
}