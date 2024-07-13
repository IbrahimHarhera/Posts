package com.ibrahim.task.domain.mapper

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class PostMapper @Inject constructor():BaseMapper<Post,RequestPost> {
    override fun map(model: Post): RequestPost {
        return RequestPost(
            id = model.id,
            title = model.title,
            body = model.body,
            realId = model.realId
        )
    }
}