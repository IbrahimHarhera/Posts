package com.ibrahim.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val uploaded: Boolean = false,
    val id: Int = 1,
    val title: String? = null,
    val body: String? = null,
    val realId: Int? = 1
) : Parcelable