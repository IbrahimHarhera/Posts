package com.ibrahim.task.domain.useCases

import com.ibrahim.task.data.model.Post
import com.ibrahim.task.data.repo.LocalRepo
import com.ibrahim.task.data.repo.RemoteRepo
import com.ibrahim.task.domain.manager.IWorkManager
import com.ibrahim.task.presentation.utils.DELETE
import com.ibrahim.utility.connectivity.NetworkConnectivity
import com.ibrahim.utility.dispatchers.IoDispatcher
import com.ibrahim.utility.usecases.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val remoteRepo: RemoteRepo,
    private val localRepo: LocalRepo,
    private val connectivity: NetworkConnectivity,
    private val workManager: IWorkManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseUseCase<Int, Post>(ioDispatcher) {
    override fun execute(params: Int): Flow<Post> = flow {

        if (connectivity.isConnected()) {
            val result: Response<ResponseBody> = remoteRepo.deletePost(params)
            if (result.isSuccessful)
                localRepo.deletePost(params)

            emit(
                Post(
                    uploaded = true,
                    id = params
                )
            )
        } else {
            workManager.enqueuePost(
                id = params,
                action = DELETE
            )
            emit(
                Post(
                    uploaded = false,
                    id = params
                )
            )
        }
    }
}