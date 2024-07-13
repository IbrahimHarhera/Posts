package com.ibrahim.task.data.repo

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost

interface LocalRepo {

    suspend fun getPosts(): List<Post?>?

    suspend fun addPost(post: RequestPost)

    suspend fun updatePost(post: RequestPost)

    suspend fun deletePost(id: Int)

    suspend fun deleteAll()
}