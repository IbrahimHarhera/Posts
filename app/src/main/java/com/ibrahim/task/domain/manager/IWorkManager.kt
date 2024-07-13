package com.ibrahim.task.domain.manager

interface IWorkManager {
    fun enqueuePost(
        id: Int,
        title: String? = null,
        body: String? = null,
        userID: Int? = null,
        action: String
    )
}