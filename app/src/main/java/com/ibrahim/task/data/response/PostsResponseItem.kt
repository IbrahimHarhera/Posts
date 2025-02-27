package com.ibrahim.task.data.response

import com.google.gson.annotations.SerializedName

data class PostsResponseItem(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("body")
    val body: String? = null,
    @SerializedName("userId")
    val realId: Int? = null
)
