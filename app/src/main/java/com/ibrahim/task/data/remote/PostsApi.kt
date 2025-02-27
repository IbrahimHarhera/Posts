package com.ibrahim.task.data.remote

import com.ibrahim.task.data.request.PostsRequestItem
import com.ibrahim.task.data.response.PostsResponseItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostsApi {

    @GET("posts")
    suspend fun getPosts(): List<PostsResponseItem>

    @POST("posts")
    suspend fun addPost(@Body post: PostsRequestItem): PostsResponseItem

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostsRequestItem): PostsResponseItem

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<ResponseBody>
}