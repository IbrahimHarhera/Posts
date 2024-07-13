package com.ibrahim.task.data.repo

import com.ibrahim.task.data.mapper.remote.PostItemMapper
import com.ibrahim.task.data.mapper.remote.PostRequestMapper
import com.ibrahim.task.data.mapper.remote.PostsResponseMapper
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.task.data.remote.PostsApi
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RemoteRepoImp @Inject constructor(
    private val postServiceApi: PostsApi,
    private val postRequestMapper: PostRequestMapper,
    private val postsResponseMapper: PostsResponseMapper,
    private val postItemMapper: PostItemMapper
) : RemoteRepo {
    override suspend fun getPosts(): List<Post?>? =postsResponseMapper.map(postServiceApi.getPosts())

    override suspend fun addPost(post: RequestPost): Post =postItemMapper.map(postServiceApi.addPost(postRequestMapper.map(post)))

    override suspend fun updatePost(id: Int, post: RequestPost): Post =postItemMapper.map(postServiceApi.updatePost(id,postRequestMapper.map(post)))

    override suspend fun deletePost(id: Int): Response<ResponseBody> =postServiceApi.deletePost(id)

}