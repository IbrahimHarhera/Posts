package com.ibrahim.network.model

import com.google.gson.annotations.SerializedName

open class NetworkModel {
    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("error")
    val error: Error? = null
}

class Error(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("info")
    val info: String? = null
)