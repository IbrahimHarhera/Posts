package com.ibrahim.task.data.di


import com.ibrahim.network.di.Annotations
import com.ibrahim.task.data.remote.PostsApi
import com.ibrahim.task.data.repo.LocalRepo
import com.ibrahim.task.data.repo.LocalRepoImp
import com.ibrahim.task.data.repo.RemoteRepo
import com.ibrahim.task.data.repo.RemoteRepoImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PostsRepoModule {

    @Binds
    abstract fun bindRemoteRepository(repository: RemoteRepoImp): RemoteRepo

    @Binds
    abstract fun bindLocalRepository(repository: LocalRepoImp): LocalRepo

}

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {
    @Provides
    @Singleton
    fun provideApiService(
        @Annotations.CoreNetwork
        retrofit: Retrofit
    ): PostsApi = retrofit.create(
        PostsApi::class.java
    )
}