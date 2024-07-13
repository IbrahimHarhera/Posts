package com.ibrahim.task.data.repo

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteRepo {

    suspend fun getPosts(): List<Post?>?

    suspend fun addPost(post: RequestPost): Post

    suspend fun updatePost(id: Int, post: RequestPost): Post

    suspend fun deletePost(id: Int): Response<ResponseBody>
}