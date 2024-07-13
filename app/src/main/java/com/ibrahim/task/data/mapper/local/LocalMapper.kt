package com.ibrahim.task.data.mapper.local


import com.ibrahim.db.entity.Post
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class LocalMapper @Inject constructor() : BaseMapper<RequestPost, Post> {
    override fun map(model: RequestPost): Post {
        return Post(
            id = model.id ?: 1,
            title = model.title,
            body = model.title,
            realId = model.realId
        )
    }
}