package com.ibrahim.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ibrahim.db.entity.Post

@Dao
interface PostDao {
    @Query("SeLECT * FROM POST")
    fun getAllPosts(): List<Post>

    @Query("DELETE FROM post WHERE id = :id")
    suspend fun deletePost(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

    @Query("DELETE FROM post")
    fun deleteAll()
}