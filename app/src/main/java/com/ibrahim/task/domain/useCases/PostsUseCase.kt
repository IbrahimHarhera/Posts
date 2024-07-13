package com.ibrahim.task.domain.useCases

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.repo.LocalRepo
import com.ibrahim.task.data.repo.RemoteRepo
import com.ibrahim.task.domain.mapper.PostMapper
import com.ibrahim.utility.connectivity.NetworkConnectivity
import com.ibrahim.utility.dispatchers.IoDispatcher
import com.ibrahim.utility.usecases.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostsUseCase @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo,
    private val checkConnection: NetworkConnectivity,
    private val postRequestModelMapper: PostMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<Post?>?>(ioDispatcher){
    override fun execute(params: Unit): Flow<List<Post?>?> = flow {
        if (checkConnection.isConnected()) {
            val result = remoteRepo.getPosts()
            if (result?.isNotEmpty() == true) {
                localRepo.deleteAll()
                result.forEach { item ->
                    item?.let { localRepo.addPost(postRequestModelMapper.map(it)) }
                }
            }
            emit(result)
        } else {
            emit(localRepo.getPosts())
        }
    }
}