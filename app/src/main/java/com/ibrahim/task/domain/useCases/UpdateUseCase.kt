package com.ibrahim.task.domain.useCases

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.model.RequestPost
import com.ibrahim.task.data.repo.LocalRepo
import com.ibrahim.task.data.repo.RemoteRepo
import com.ibrahim.task.domain.manager.IWorkManager
import com.ibrahim.task.presentation.utils.UPDATE
import com.ibrahim.utility.connectivity.NetworkConnectivity
import com.ibrahim.utility.dispatchers.IoDispatcher
import com.ibrahim.utility.usecases.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUseCase @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo,
    private val networkConnectivity: NetworkConnectivity,
    private val workManger: IWorkManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseUseCase<RequestPost, Post>(ioDispatcher) {

    override fun execute(params: RequestPost): Flow<Post> = flow {

        if (networkConnectivity.isConnected()) {
            val result = remoteRepo.updatePost(params.id ?: 1, params)
            if (result.uploaded)
                localRepo.updatePost(params)
            emit(result)
        } else {
            workManger.enqueuePost(
                params.id ?: 1,
                params.title ?: "",
                params.body ?: "",
                params.realId ?: 1,
                UPDATE
            )
            emit(
                Post(
                    uploaded  = false,
                    id = params.id ?: 1,
                    params.title ?: "",
                    params.body ?: "",
                    params.realId ?: 1,
                )
            )

        }
    }

}