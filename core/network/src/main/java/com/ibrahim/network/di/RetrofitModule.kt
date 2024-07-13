package com.ibrahim.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ibrahim.network.network.adapter.ApiCallAdapterFactory
import com.ibrahim.network.utils.Headers
import com.ibrahim.network.utils.Headers.Keys.BASE_URL
import com.ibrahim.ui.resProvider.IResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Annotations.CoreNetwork
    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        apiCallAdapterFactory: ApiCallAdapterFactory,
        @Annotations.CoreNetwork okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().applyCommonConfiguration(apiCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient)
        .baseUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun provideApiCallFactory(iResourceProvider: IResourceProvider) = ApiCallAdapterFactory(iResourceProvider)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Annotations.CoreNetwork
    @Provides
    @Singleton
    fun provideOkHttpClient(
        headersInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .applyCommonConfiguration(
            headersInterceptor = headersInterceptor,
            loggingInterceptor = loggingInterceptor,
        )
        .retryOnConnectionFailure(false)
        .build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideHeadersInterceptor(): Interceptor =
        Interceptor { chain ->
            val newRequest = chain
                .request()
                .newBuilder()
                .addHeader(Headers.Keys.ACCEPT, Headers.Values.ACCEPT_VALUE)
                .addHeader(Headers.Keys.CONTENT_TYPE, Headers.Values.ACCEPT_VALUE)
                .build()
            chain.proceed(newRequest)
        }
}