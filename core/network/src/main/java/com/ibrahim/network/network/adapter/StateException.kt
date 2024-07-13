package com.ibrahim.network.network.adapter

fun stateException(errorMessage: String? = null): Nothing = errorMessage?.let {
    throw IllegalStateException(it)
} ?: throw IllegalStateException()