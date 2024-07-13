package com.ibrahim.task.presentation.utils

sealed class PostStatus {
    data object IsValid : PostStatus()
    data object Invalid : PostStatus()
}