package com.ibrahim.task.data.mapper.remote

import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.task.data.request.PostsRequestItem
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class PostRequestMapper @Inject constructor() : BaseMapper<RequestPost, PostsRequestItem> {

    override fun map(model: RequestPost): PostsRequestItem {
        return PostsRequestItem(
            userId = model.realId, id = model.id, title = model.title, body = model.body
        )
    }
}