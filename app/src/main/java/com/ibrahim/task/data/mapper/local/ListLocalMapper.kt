package com.ibrahim.task.data.mapper.local

import com.ibrahim.db.entity.Post
import com.ibrahim.utility.mappers.BaseMapper
import javax.inject.Inject

class ListLocalMapper @Inject constructor() :
    BaseMapper<List<Post>, List<com.ibrahim.task.data.model.Post?>?> {

    override fun map(model: List<Post>): List<com.ibrahim.task.data.model.Post?>? {
        return model.map {
            com.ibrahim.task.data.model.Post(
                uploaded = model.isNotEmpty(),
                id = it.id,
                title = it.title,
                body = it.body,
                realId = it.realId,
            )
        }
    }
}