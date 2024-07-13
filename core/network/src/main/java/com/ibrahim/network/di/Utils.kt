package com.ibrahim.network.di

import com.ibrahim.network.BuildConfig
import com.ibrahim.network.network.adapter.ApiCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 60L

fun OkHttpClient.Builder.applyCommonConfiguration(
    headersInterceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient.Builder {
    this
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(headersInterceptor)
    if (BuildConfig.DEBUG)
        this.addNetworkInterceptor(loggingInterceptor)

    return this
}

fun Retrofit.Builder.applyCommonConfiguration(apiCallAdapterFactory: ApiCallAdapterFactory): Retrofit.Builder {
    addCallAdapterFactory(apiCallAdapterFactory)
    return this
}