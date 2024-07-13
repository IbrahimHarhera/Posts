package com.ibrahim.task.data.mapper.remote

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.response.PostsResponseItem
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class PostItemMapper @Inject constructor() : BaseMapper<PostsResponseItem, Post> {
    override fun map(model: PostsResponseItem): Post {
        return Post(
            uploaded = model.id != 0,
            id = model.id ?: 0,
            title = model.title,
            body = model.body,
            realId = model.realId
        )
    }
}