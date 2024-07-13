package com.ibrahim.task.data.mapper.remote

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.response.PostsResponseItem
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class PostsResponseMapper @Inject constructor() :
    BaseMapper<List<PostsResponseItem?>?, List<Post?>?> {

    override fun map(model: List<PostsResponseItem?>?): List<Post?>? {
        return model?.map {
            Post(
                uploaded = model.isNotEmpty(),
                id = it?.id ?: 0,
                title = it?.title,
                body = it?.body,
                realId = it?.realId,
            )
        }
    }
}