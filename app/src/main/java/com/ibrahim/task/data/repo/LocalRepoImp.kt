package com.ibrahim.task.data.repo

import com.ibrahim.db.PostDao
import com.ibrahim.task.data.mapper.local.ListLocalMapper
import com.ibrahim.task.data.mapper.local.LocalMapper
import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import javax.inject.Inject

class LocalRepoImp @Inject constructor(
    private val dao: PostDao,
    private val localMapper: LocalMapper,
    private val listLocalMapper: ListLocalMapper
) : LocalRepo {
    override suspend fun getPosts(): List<Post?>? = listLocalMapper.map(dao.getAllPosts())

    override suspend fun addPost(post: RequestPost) = dao.insertPost(localMapper.map(post))

    override suspend fun updatePost(post: RequestPost) = dao.updatePost(localMapper.map(post))

    override suspend fun deletePost(id: Int) = dao.deletePost(id)

    override suspend fun deleteAll() = dao.deleteAll()
}